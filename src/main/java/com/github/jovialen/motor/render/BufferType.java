package com.github.jovialen.motor.render;

import org.lwjgl.opengl.GL11;

public enum BufferType {
    FLOAT, FLOAT2, FLOAT3, FLOAT4,
    INT, INT2, INT3, INT4;

    public int getGlType() {
        return switch (this) {
            case FLOAT, FLOAT2, FLOAT3, FLOAT4 -> GL11.GL_FLOAT;
            case INT, INT2, INT3, INT4 -> GL11.GL_INT;
        };
    }

    public int getComponentCount() {
        return switch (this) {
            case FLOAT, INT -> 1;
            case FLOAT2, INT2 -> 2;
            case FLOAT3, INT3 -> 3;
            case FLOAT4, INT4 -> 4;
        };
    }

    public int getStride() {
        return getTypeStride() * getComponentCount();
    }

    private int getTypeStride() {
        return switch (getGlType()) {
            case GL11.GL_FLOAT -> Float.BYTES;
            case GL11.GL_INT -> Integer.BYTES;
            default -> throw new IllegalStateException("Unexpected value: " + getGlType());
        };
    }
}
