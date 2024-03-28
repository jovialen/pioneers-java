package com.github.jovialen.motor.render.resource.layout.vertex;

public class BufferBinding {
    public int offset;
    public int stride;
    public BufferInputRate inputRate;

    public BufferBinding(int stride) {
        this(0, stride);
    }

    public BufferBinding(int offset, int stride) {
        this(offset, stride, BufferInputRate.PER_VERTEX);
    }

    public BufferBinding(int offset, int stride, BufferInputRate inputRate) {
        this.offset = offset;
        this.stride = stride;
        this.inputRate = inputRate;
    }
}
