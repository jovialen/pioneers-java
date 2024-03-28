package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.render.node.MigratedCameraNode;
import com.github.jovialen.motor.render.Camera;
import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.Surface;
import com.github.jovialen.motor.window.Window;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

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
        PriorityQueue<MigratedCameraNode> cameras = getCameras();
        Window window = renderRoot.getWindow();

        GLState state = GLState.getCurrent();
        for (MigratedCameraNode cameraNode : cameras) {
            cameraNode.render(state);
        }

        window.present();
    }

    private PriorityQueue<MigratedCameraNode> getCameras() {
        List<MigratedCameraNode> cameraNodes = renderRoot.getChildren(MigratedCameraNode.class);
        PriorityQueue<MigratedCameraNode> cameras = new PriorityQueue<>(Comparator.comparingInt((a) -> a.camera.priority));
        cameras.addAll(cameraNodes);
        return cameras;
    }
}
