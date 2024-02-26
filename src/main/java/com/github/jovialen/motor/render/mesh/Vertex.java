package com.github.jovialen.motor.render.mesh;

import com.github.jovialen.motor.render.gl.BufferType;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Vertex {
    public static final BufferType[] LAYOUT = { BufferType.FLOAT3, BufferType.FLOAT2 };

    public Vector3f position = new Vector3f(0);
    public Vector2f uv = new Vector2f(0);

    public Vertex() {
    }

    public Vertex(Vector3f position) {
        this.position = position;
    }

    public Vertex(Vector3f position, Vector2f uv) {
        this.position = position;
        this.uv = uv;
    }

    public FloatBuffer put(FloatBuffer buffer) {
        buffer.put(position.x);
        buffer.put(position.y);
        buffer.put(position.z);
        buffer.put(uv.x);
        buffer.put(uv.y);
        return buffer;
    }

    public static int getVertexSize() {
        return Arrays.stream(LAYOUT)
                .map(BufferType::getComponentCount)
                .reduce(0, Integer::sum);
    }
}
