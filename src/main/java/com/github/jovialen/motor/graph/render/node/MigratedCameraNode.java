package com.github.jovialen.motor.graph.render.node;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.render.Camera;
import org.joml.Matrix4f;

public class MigratedCameraNode extends RenderNode {
    public Matrix4f transform = new Matrix4f().identity();
    public Matrix4f projection = new Matrix4f().identity();

    public Camera camera = new Camera();

    public MigratedCameraNode(RenderNode parent) {
        super(parent);
    }
}
