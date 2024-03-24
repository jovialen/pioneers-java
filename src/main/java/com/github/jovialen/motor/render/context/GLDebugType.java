package com.github.jovialen.motor.render.context;

import org.lwjgl.opengl.GL43;

public enum GLDebugType {
    ERROR, DEPRECATED_BEHAVIOR, UNDEFINED_BEHAVIOR, PORTABILITY, PERFORMANCE, MARKER, PUSH_GROUP, POP_GROUP, OTHER;

    @Override
    public String toString() {
        return switch (this) {
            case ERROR -> "Error";
            case DEPRECATED_BEHAVIOR -> "Deprecated Behavior";
            case UNDEFINED_BEHAVIOR -> "Undefined Behavior";
            case PORTABILITY -> "Portability";
            case PERFORMANCE -> "Performance";
            case MARKER -> "Marker";
            case PUSH_GROUP -> "Push Group";
            case POP_GROUP -> "Pop Group";
            case OTHER -> "Other";
        };
    }

    public static GLDebugType fromGl(int glType) {
        return switch (glType) {
            case GL43.GL_DEBUG_TYPE_ERROR -> ERROR;
            case GL43.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR -> DEPRECATED_BEHAVIOR;
            case GL43.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR -> UNDEFINED_BEHAVIOR;
            case GL43.GL_DEBUG_TYPE_PORTABILITY -> PORTABILITY;
            case GL43.GL_DEBUG_TYPE_PERFORMANCE -> PERFORMANCE;
            case GL43.GL_DEBUG_TYPE_MARKER -> MARKER;
            case GL43.GL_DEBUG_TYPE_PUSH_GROUP -> PUSH_GROUP;
            case GL43.GL_DEBUG_TYPE_POP_GROUP -> POP_GROUP;
            case GL43.GL_DEBUG_TYPE_OTHER -> OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + glType);
        };
    }

    public static int all() {
        return GL43.GL_DONT_CARE;
    }
}
