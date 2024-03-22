package com.github.jovialen.motor.render;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class Camera {
    public Vector4f clearColor = new Vector4f(1);
    public float fov = 70.0f;
    public float aspect = (float) 800 / 600;
    public float zNear = 0.01f;
    public float zFar = 1000.0f;
    public int priority = 0;

    public Matrix4f projection = new Matrix4f().identity();

    public void setAspect(Vector2i resolution) {
        aspect = (float) resolution.x / (float) resolution.y;
    }

    public void updateProjection() {
        projection.setPerspective(fov, aspect, zNear, zFar);
    }

    public void set(Camera other) {
        clearColor.set(other.clearColor);
        fov = other.fov;
        aspect = other.aspect;
        zNear = other.zNear;
        zFar = other.zFar;
        priority = other.priority;

        projection.set(other.projection);
    }
}
