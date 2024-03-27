package com.github.jovialen.motor.render.resource.buffer;

import com.github.jovialen.motor.render.resource.layout.DataType;

import java.nio.FloatBuffer;

public class IndexBuffer extends Buffer {
    @Override
    public final void store(FloatBuffer floatBuffer) {
        throw new RuntimeException("Storing float data in index buffer");
    }

    @Override
    public DataType getDataType() {
        return DataType.UNSIGNED_INT;
    }
}
