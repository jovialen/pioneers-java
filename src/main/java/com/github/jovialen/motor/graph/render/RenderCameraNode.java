package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.render.resource.Surface;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class RenderCameraNode extends RenderNode {
    public Matrix4f transform = new Matrix4f().identity();
    public Matrix4f projection = new Matrix4f().identity();

    public Surface target = null;
    public Vector4f clearColor = new Vector4f();

    public RenderCameraNode(RenderNode parent) {
        super(parent);
    }
}
