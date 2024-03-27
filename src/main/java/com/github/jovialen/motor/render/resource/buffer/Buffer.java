package com.github.jovialen.motor.render.resource.buffer;

import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.DestructibleResource;
import com.github.jovialen.motor.render.resource.layout.DataType;
import org.lwjgl.opengl.GL15;
import org.tinylog.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer implements DestructibleResource {
    private final int id;
    private DataType dataType;
    private int capacity = 0;
    private int size = 0;

    public Buffer() {
        this(GL15.glGenBuffers());
    }

    public Buffer(int id) {
        Logger.tag("GL").info("Creating buffer {}", id);
        this.id = id;
    }

    @Override
    public void destroy() {
        Logger.tag("GL").info("Destroying buffer {}", id);
        GL15.glDeleteBuffers(id);
    }

    public void allocate(int capacity) {
        if (this.capacity >= capacity) return;
        this.capacity = capacity;

        Logger.tag("GL").debug("Allocating {} bytes of data for buffer {}", capacity, id);

        try (GLState glState = GLState.pushSharedState()) {
            glState.bindVertexBuffer(id);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, capacity, GL15.GL_STATIC_DRAW);
        }
    }

    public void store(IntBuffer floatBuffer) {
        store(floatBuffer, DataType.INT);
    }

    public void store(IntBuffer intBuffer, DataType dataType) {
        this.dataType = dataType;
        size = intBuffer.remaining();
        try (GLState glState = GLState.pushSharedState()) {
            glState.bindVertexBuffer(id);
            allocate(size * Integer.BYTES);
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, intBuffer);
        }
    }

    public void store(FloatBuffer floatBuffer) {
        store(floatBuffer, DataType.FLOAT);
    }

    public void store(FloatBuffer floatBuffer, DataType dataType) {
        this.dataType = dataType;
        size = floatBuffer.remaining();
        try (GLState glState = GLState.pushSharedState()) {
            glState.bindVertexBuffer(id);
            allocate(size * Float.BYTES);
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, floatBuffer);
        }
    }

    public int getId() {
        return id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }
}
