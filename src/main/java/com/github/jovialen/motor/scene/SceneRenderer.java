package com.github.jovialen.motor.scene;

import com.github.jovialen.motor.render.gl.GLContext;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class SceneRenderer {
    private final GLContext context;

    public SceneRenderer(GLContext context) {
        this.context = context;
    }

    public void submit(SceneNode root) {
        context.activate();

        List<Camera3DNode> cameras = root.getChildrenOfClass(Camera3DNode.class);
        for (Camera3DNode camera : cameras) {
            GL11.glClearColor(camera.clearColor.x, camera.clearColor.y, camera.clearColor.y, camera.clearColor.w);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        }

        context.flush();
    }
}
