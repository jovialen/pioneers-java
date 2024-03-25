package com.github.jovialen.motor.graph.scene.renderable;

import com.github.jovialen.motor.graph.render.RenderCameraNode;
import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.Camera;
import com.github.jovialen.motor.render.resource.Surface;

import java.util.Objects;

public class CameraNode extends Node3D implements MigrationNode<RenderCameraNode> {
    public Camera camera = new Camera();

    public CameraNode(SceneNode parent) {
        super(parent);
    }

    @Override
    public RenderCameraNode migrate(RenderNode parent) {
        return new RenderCameraNode(parent);
    }

    @Override
    public void synchronize(RenderCameraNode renderNode) {
        renderNode.camera.set(camera);
        renderNode.transform.set(transform);

        Surface surface = Objects.requireNonNullElse(camera.target, getRoot().getWindow());
        renderNode.projection.setPerspective((float) Math.toRadians(70),
                surface.getAspect(),
                camera.nearPlane,
                camera.farPlane);
    }
}
