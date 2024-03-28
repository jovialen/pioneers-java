package com.github.jovialen.motor.render.resource.layout;

import org.lwjgl.opengl.GL11;

public enum DataType {
    FLOAT, FLOAT2, FLOAT3, FLOAT4,
    INT, INT2, INT3, INT4,
    UNSIGNED_INT,
    MAT4;

    public int size() {
        return switch (this) {
            case MAT4, FLOAT, FLOAT2, FLOAT3, FLOAT4 -> Float.BYTES * this.componentCount();
            case UNSIGNED_INT, INT, INT2, INT3, INT4 -> Integer.BYTES * this.componentCount();
        };
    }

    public int componentCount() {
        return switch (this) {
            case FLOAT, INT, UNSIGNED_INT -> 1;
            case FLOAT2, INT2 -> 2;
            case FLOAT3, INT3 -> 3;
            case FLOAT4, INT4 -> 4;
            case MAT4 -> 16;
        };
    }

    public int glDataType() {
        return switch (this) {
            case MAT4, FLOAT, FLOAT2, FLOAT3, FLOAT4 -> GL11.GL_FLOAT;
            case INT, INT2, INT3, INT4 -> GL11.GL_INT;
            case UNSIGNED_INT -> GL11.GL_UNSIGNED_INT;
        };
    }
}
