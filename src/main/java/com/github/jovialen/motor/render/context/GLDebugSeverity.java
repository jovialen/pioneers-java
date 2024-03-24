package com.github.jovialen.motor.render.context;

import org.lwjgl.opengl.GL43;

public enum GLDebugSeverity {
    HIGH, MEDIUM, LOW, NOTIFICATION;

    @Override
    public String toString() {
        return switch (this) {
            case HIGH -> "High";
            case MEDIUM -> "Medium";
            case LOW -> "Low";
            case NOTIFICATION -> "Notification";
        };
    }

    public static GLDebugSeverity fromGl(int glSeverity) {
        return switch (glSeverity) {
            case GL43.GL_DEBUG_SEVERITY_HIGH -> HIGH;
            case GL43.GL_DEBUG_SEVERITY_MEDIUM -> MEDIUM;
            case GL43.GL_DEBUG_SEVERITY_LOW -> LOW;
            case GL43.GL_DEBUG_SEVERITY_NOTIFICATION -> NOTIFICATION;
            default -> throw new IllegalStateException("Unexpected value: " + glSeverity);
        };
    }

    public static int all() {
        return GL43.GL_DONT_CARE;
    }
}
