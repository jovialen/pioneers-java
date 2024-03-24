package com.github.jovialen.motor.render.context;

import org.lwjgl.opengl.GL43;

public enum GLDebugSource {
    API, WINDOW_SYSTEM, SHADER_COMPILER, THIRD_PARTY, APPLICATION, OTHER;

    @Override
    public String toString() {
        return switch (this) {
            case API -> "API";
            case WINDOW_SYSTEM -> "Window System";
            case SHADER_COMPILER -> "Shader Compiler";
            case THIRD_PARTY -> "Third Party";
            case APPLICATION -> "Application";
            case OTHER -> "Other";
        };
    }

    public static GLDebugSource fromGl(int glSource) {
        return switch (glSource) {
            case GL43.GL_DEBUG_SOURCE_API -> API;
            case GL43.GL_DEBUG_SOURCE_WINDOW_SYSTEM -> WINDOW_SYSTEM;
            case GL43.GL_DEBUG_SOURCE_SHADER_COMPILER -> SHADER_COMPILER;
            case GL43.GL_DEBUG_SOURCE_THIRD_PARTY -> THIRD_PARTY;
            case GL43.GL_DEBUG_SOURCE_APPLICATION -> APPLICATION;
            case GL43.GL_DEBUG_SOURCE_OTHER -> OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + glSource);
        };
    }

    public static int all() {
        return GL43.GL_DONT_CARE;
    }
}
