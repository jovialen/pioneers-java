package com.github.jovialen.motor.utils;

import com.github.jovialen.motor.render.resource.layout.DataType;
import org.joml.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class DataUtils {
    public static int sizeOf(DataType... dataTypes) {
        int size = 0;
        for (DataType type : dataTypes) {
            size += type.size();
        }
        return size;
    }

    public static void store(FloatBuffer buffer, Vector2f data) {
        data.get(buffer);
        buffer.position(buffer.position() + 2);
    }

    public static void store(FloatBuffer buffer, Vector3f data) {
        data.get(buffer);
        buffer.position(buffer.position() + 3);
    }

    public static void store(FloatBuffer buffer, Vector4f data) {
        data.get(buffer);
        buffer.position(buffer.position() + 4);
    }

    public static void store(IntBuffer buffer, Vector2i data) {
        data.get(buffer);
        buffer.position(buffer.position() + 2);
    }

    public static void store(IntBuffer buffer, Vector3i data) {
        data.get(buffer);
        buffer.position(buffer.position() + 3);
    }

    public static void store(IntBuffer buffer, Vector4i data) {
        data.get(buffer);
        buffer.position(buffer.position() + 4);
    }

    public static void store(FloatBuffer dataBuffer, Matrix4f data) {
        data.get(dataBuffer);
        dataBuffer.position(dataBuffer.position() + 16);
    }
}
