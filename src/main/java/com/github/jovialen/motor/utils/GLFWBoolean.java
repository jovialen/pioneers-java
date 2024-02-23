package com.github.jovialen.motor.utils;

import org.lwjgl.glfw.GLFW;

public class GLFWBoolean {
    private boolean value;

    public GLFWBoolean(boolean value) {
        this.value = value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    public int asGlfw() {
        return value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
    }
}
