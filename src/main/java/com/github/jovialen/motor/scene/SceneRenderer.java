package com.github.jovialen.motor.scene;

import com.github.jovialen.motor.render.gl.GLContext;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Renderer for a scene graph.
 */
public class SceneRenderer {
    private final GLContext context;
    private List<Camera3DNode> cameras = new ArrayList<>();

    public SceneRenderer(GLContext context) {
        this.context = context;
    }

    /**
     * Synchronize the renderer with a scene graph.
     * @param root Root of the scene graph to synchronize with.
     */
    public void sync(SceneNode root) {
        root.sync();

        cameras = root.getChildrenOfClass(Camera3DNode.class);
    }

    /**
     * Render the scene graph the renderer last synchronized with.
     *
     * To update the render, the scene graph must be re-synchronized.
     */
    public void render() {
        for (Camera3DNode camera : cameras) {
            GL11.glClearColor(camera.clearColor.x, camera.clearColor.y, camera.clearColor.z, camera.clearColor.w);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        }

        context.flush();
    }

    public GLContext getContext() {
        return context;
    }
}
