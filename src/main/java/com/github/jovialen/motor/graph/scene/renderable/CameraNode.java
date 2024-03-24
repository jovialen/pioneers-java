package com.github.jovialen.motor.graph.scene.renderable;

import com.github.jovialen.motor.graph.render.RenderCameraNode;
import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.resource.Surface;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class CameraNode extends Node3D implements MigrationNode<RenderCameraNode> {
    public Surface target;
    public Vector4f clearColor = new Vector4f(1);

    public CameraNode(SceneNode parent) {
        super(parent);
    }

    @Override
    public RenderCameraNode migrate(RenderNode parent) {
        return new RenderCameraNode(parent);
    }

    @Override
    public void synchronize(RenderCameraNode renderNode) {
        renderNode.transform.set(transform);

        Vector2i resolution = getRoot().getWindow().getResolution();
        float aspect = (float) resolution.x / (float) resolution.y;
        renderNode.projection.setPerspective((float) Math.toRadians(70), aspect, 0.1f, 100.0f);

        renderNode.target = target;
        renderNode.clearColor.set(clearColor);
    }
}
