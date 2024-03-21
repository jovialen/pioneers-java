package com.github.jovialen.motor.scene;

import com.github.jovialen.motor.render.gl.GLContext;
import com.github.jovialen.motor.render.mesh.MeshBuffer;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import com.github.jovialen.motor.scene.nodes.MeshNode;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Renderer for a scene graph.
 */
public class SceneRenderer {
    private final GLContext context;
    private List<Camera3DNode> cameras = new ArrayList<>();
    private List<MeshNode> meshes = new ArrayList<>();

    public SceneRenderer(GLContext context) {
        this.context = context;
    }

    /**
     * Synchronize the renderer with a scene graph.
     * @param root Root of the scene graph to synchronize with.
     */
    public void sync(SceneRoot root) {
        root.sync(this);

        cameras = root.getChildrenOfClass(Camera3DNode.class);
        meshes = root.getChildrenOfClass(MeshNode.class);
    }

    /**
     * Render the scene graph the renderer last synchronized with.
     *
     * To update the render, the scene graph must be re-synchronized.
     */
    public void render() {
        for (Camera3DNode camera : cameras) {
            Vector2i resolution = getContext().getContextWindow().getSize();
            GL11.glViewport(0, 0, resolution.x, resolution.y);

            GL11.glClearColor(camera.clearColor.x, camera.clearColor.y, camera.clearColor.z, camera.clearColor.w);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            for (MeshNode mesh : meshes) {
                MeshBuffer meshBuffer = mesh.meshBuffer;
                meshBuffer.bind();

                GL11.glDrawElements(GL11.GL_TRIANGLES,
                        mesh.mesh.indices.size(),
                        GL11.GL_UNSIGNED_INT,
                        0);

                meshBuffer.unbind();
            }
        }

        context.flush();
    }

    public GLContext getContext() {
        return context;
    }
}
