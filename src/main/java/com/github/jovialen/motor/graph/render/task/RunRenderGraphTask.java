package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderCameraNode;
import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.render.resource.Surface;
import com.github.jovialen.motor.window.Window;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class RunRenderGraphTask extends RenderGraphTask {
    public RunRenderGraphTask(RenderRoot renderRoot) {
        super(renderRoot);
    }

    @Override
    public void invoke() {
        PriorityQueue<RenderCameraNode> cameras = getCameras();
        Window window = renderRoot.getWindow();

        for (RenderCameraNode camera : cameras) {
            Surface target = Objects.requireNonNullElse(camera.target, window);
            Vector2i resolution = target.getResolution();
            Vector4f clearColor = camera.clearColor;

            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, target.getId());
            GL11.glViewport(0, 0, resolution.x, resolution.y);
            GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            renderRoot.run();
        }

        window.present();
    }

    private PriorityQueue<RenderCameraNode> getCameras() {
        List<RenderCameraNode> cameraNodes = renderRoot.getChildren(RenderCameraNode.class);
        PriorityQueue<RenderCameraNode> cameras = new PriorityQueue<>(Comparator.comparingInt((a) -> a.priority));
        cameras.addAll(cameraNodes);
        return cameras;
    }
}
