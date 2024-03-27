package com.github.jovialen.motor.render.resource.shader;

import org.lwjgl.opengl.GL20;

public enum ShaderStage {
    VERTEX, FRAGMENT;

    public int glStage() {
        return switch (this) {
            case VERTEX -> GL20.GL_VERTEX_SHADER;
            case FRAGMENT -> GL20.GL_FRAGMENT_SHADER;
        };
    }
}
