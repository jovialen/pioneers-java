package com.github.jovialen.motor.scene.renderer;

import com.github.jovialen.motor.render.Camera;
import com.github.jovialen.motor.render.Model;
import com.github.jovialen.motor.render.gl.ShaderProgram;
import com.github.jovialen.motor.render.pipeline.Pipeline;
import com.github.jovialen.motor.render.gl.GLContext;
import com.github.jovialen.motor.render.gl.Texture2D;
import com.github.jovialen.motor.render.image.Image;
import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.MeshBuffer;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.utils.Pair;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Renderer for a scene graph.
 */
public class SceneRenderer {
    private final GLContext context;

    private final PriorityQueue<Pair<Camera, Matrix4f>> cameras = new PriorityQueue<>(Comparator.comparingInt(a -> a.a().priority));
    private final HashMap<Pipeline, HashMap<Model, ArrayList<Matrix4f>>> actors = new HashMap<>();
    private final HashMap<Mesh, MeshBuffer> meshBuffers = new HashMap<>();
    private final HashMap<Image, Texture2D> textures = new HashMap<>();
    private final HashMap<Pipeline, ShaderProgram> shaderPrograms = new HashMap<>();
    private boolean ready = false;

    public SceneRenderer(GLContext context) {
        this.context = context;
    }

    public void destroy() {
        meshBuffers.values().forEach(MeshBuffer::destroy);
        textures.values().forEach(Texture2D::destroy);
    }

    /**
     * Synchronize the renderer with a scene graph.
     *
     * @param root Root of the scene graph to synchronize with.
     */
    public void sync(SceneRoot root) {
        root.sync();

        if (!ready) {
            validate(root);
        }
    }

    /**
     * Render the scene graph the renderer last synchronized with.
     * <br/><br/>
     * To update the render, the scene graph must be re-synchronized.
     */
    public void render() {
        if (!ready) {
            Logger.tag("RENDER").error("Attempting to render when not ready. Synchronize with scene first.");
            return;
        }

        Matrix4f mvpMatrix = new Matrix4f().identity();

        for (Pair<Camera, Matrix4f> cameraMatrixPair : cameras) {
            Camera camera = cameraMatrixPair.a();
            Matrix4f viewMatrix = cameraMatrixPair.b();
            Matrix4f projectionMatrix = camera.projection;

            mvpMatrix.mul(projectionMatrix).mul(viewMatrix);

            Vector2i resolution = getContext().getContextWindow().getSize();
            GL11.glViewport(0, 0, resolution.x, resolution.y);

            Vector4f clearColor = camera.clearColor;
            GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            for (Pipeline pipeline : actors.keySet()) {
                if (pipeline.useDefaultShaderProgram()) {
                    ShaderProgram.DEFAULT.use();
                } else {
                    shaderPrograms.get(pipeline).use();
                }

                if (pipeline.isBlending()) {
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(pipeline.getBlendSrc(), pipeline.getBlendDst());
                } else {
                    GL11.glDisable(GL11.GL_BLEND);
                }

                for (Model model : actors.get(pipeline).keySet()) {
                    MeshBuffer meshBuffer = meshBuffers.get(model.mesh);
                    meshBuffer.bind();

                    for (int i = 0; i < model.images.length; i++) {
                        Texture2D texture = textures.get(model.images[i]);
                        texture.bind(i);
                    }

                    for (Matrix4f modelMatrix : actors.get(pipeline).get(model)) {
                        mvpMatrix.mul(modelMatrix);

                        GL11.glDrawElements(GL11.GL_TRIANGLES,
                                model.mesh.indices.size(),
                                GL11.GL_UNSIGNED_INT,
                                0);
                    }
                }
            }
        }

        context.flush();
    }

    public void addCamera(Camera camera, Matrix4f transform) {
        if (transform == null) {
            Logger.tag("RENDER").error("Attempting to add camera to render without transform.");
        }

        // This is a lot simpler than what is going on below.
        cameras.add(new Pair<>(camera, transform));
    }

    public void addModel(Pipeline pipeline, Model model, Matrix4f transform) {
        // Guards
        if (pipeline == null) {
            Logger.tag("RENDER").error("Attempting to add model to render without pipeline.");
            return;
        }

        if (transform == null) {
            Logger.tag("RENDER").error("Attempting to add model to render without transform.");
            return;
        }

        if (model == null || model.mesh == null || model.images == null) {
            Logger.tag("RENDER").error("Attempting to add incomplete model to render.");
            return;
        }

        // Add the model to the renderer
        if (!actors.containsKey(pipeline)) {
            actors.put(pipeline, new HashMap<>(1));
        }
        HashMap<Model, ArrayList<Matrix4f>> models = actors.get(pipeline);

        if (!models.containsKey(model)) {
            models.put(model, new ArrayList<>(1));
        }
        ArrayList<Matrix4f> transforms = models.get(model);

        if (transforms.contains(transform)) {
            Logger.tag("RENDER").error("Actor is already in renderer. May be rendering double.");
        }
        transforms.add(transform);

        // Build OpenGL resources
        if (!meshBuffers.containsKey(model.mesh)) {
            meshBuffers.put(model.mesh, model.mesh.build());
        }

        for (Image image : model.images) {
            if (!textures.containsKey(image)) {
                textures.put(image, image.build());
            }
        }

        if (!pipeline.useDefaultShaderProgram() && !shaderPrograms.containsKey(pipeline)) {
            shaderPrograms.put(pipeline, pipeline.build());
        }
    }

    public void updateMesh(Mesh mesh) {
        if (!meshBuffers.containsKey(mesh)) {
            Logger.tag("RENDER").warn("Attempting to update mesh that is not in renderer.");
            return;
        }

        meshBuffers.get(mesh).update(mesh);
    }

    public void updateImage(Image image) {
        if (!textures.containsKey(image)) {
            Logger.tag("RENDER").warn("Attempting to update image that is not in renderer.");
            return;
        }

        // I could either make an update function, or I could just do this. I did this.
        textures.get(image).destroy();
        textures.put(image, image.build());
    }

    public GLContext getContext() {
        return context;
    }

    /**
     * Invalidate the current renderer state. Will result in a revalidation of
     * the renderer at the next synchronization with the scene.
     */
    public void invalidate() {
        Logger.tag("RENDER").debug("Invalidating current render state");
        actors.clear();
        cameras.clear();
        ready = false;
    }

    private void validate(SceneRoot scene) {
        Logger.tag("RENDER").debug("Validating new render state for {}", scene);

        scene.submitToRender(this);
        ready = true;
    }
}
