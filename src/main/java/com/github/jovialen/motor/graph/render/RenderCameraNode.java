package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.render.Camera;
import org.joml.Matrix4f;

public class RenderCameraNode extends RenderNode {
    public Matrix4f transform = new Matrix4f().identity();
    public Matrix4f projection = new Matrix4f().identity();

    public Camera camera = new Camera();

    public RenderCameraNode(RenderNode parent) {
        super(parent);
    }
}
