package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.resource.Surface;
import org.joml.Vector4f;

public class Camera {
    public Surface target;
    public Vector4f clearColor = new Vector4f(1);
    public int priority = 0;
    public int fov = 70;
    public float nearPlane = 0.1f;
    public float farPlane = 100.0f;

    public void set(Camera other) {
        this.target = other.target;
        this.clearColor.set(other.clearColor);
        this.priority = other.priority;
        this.fov = other.fov;
        this.nearPlane = other.nearPlane;
        this.farPlane = other.farPlane;
    }
}
