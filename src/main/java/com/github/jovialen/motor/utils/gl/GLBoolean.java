package com.github.jovialen.motor.utils.gl;

import org.lwjgl.opengl.GL11;

public class GLBoolean {
    private boolean value;

    public GLBoolean(boolean value) {
        this.value = value;
    }

    public GLBoolean(int value) {
        this.value = value == GL11.GL_TRUE;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public void set(int value) {
        this.value = value == GL11.GL_TRUE;
    }

    public boolean get() {
        return value;
    }

    public int asGl() {
        return value ? GL11.GL_TRUE : GL11.GL_FALSE;
    }
}
