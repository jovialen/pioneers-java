package com.github.jovialen.motor.graph.scene.renderable;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.node.MigratedCameraNode;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.Camera;
import org.joml.Matrix4f;

public class CameraNode extends Node3D implements MigrationNode<MigratedCameraNode> {
    public Camera camera = new Camera();

    public final Matrix4f view = new Matrix4f().identity();

    public CameraNode(SceneNode parent) {
        super(parent);
    }

    @Override
    public void process(double deltaTime) {
        view.set(transform);
        view.invert();

        super.process(deltaTime);
    }

    @Override
    public MigratedCameraNode migrate(RenderNode parent) {
        return new MigratedCameraNode(parent);
    }

    @Override
    public void synchronize(MigratedCameraNode renderNode) {
        renderNode.camera.set(camera);
        renderNode.view.set(view);
    }
}
