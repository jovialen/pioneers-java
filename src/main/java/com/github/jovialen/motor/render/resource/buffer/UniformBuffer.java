package com.github.jovialen.motor.render.resource.buffer;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class UniformBuffer extends Buffer {
    private FloatBuffer data;

    @Override
    public void allocate(int capacity) {
        if (this.getCapacity() < capacity) {
            data = BufferUtils.createFloatBuffer(capacity / Float.BYTES);
        }
        super.allocate(capacity);
    }

    public FloatBuffer map() {
        return data;
    }

    public void unmap() {
        data.flip();
        store(data);
    }
}
