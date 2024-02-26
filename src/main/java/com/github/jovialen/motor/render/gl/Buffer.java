package com.github.jovialen.motor.render.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.tinylog.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Buffer {
    public static class Slice {
        public final Buffer buffer;
        public final int offset;
        public final int stride;

        public Slice(Buffer buffer, int offset, int stride) {
            this.buffer = buffer;
            this.offset = offset;
            this.stride = stride;
        }
    }

    public static class IndexBuffer extends Buffer {

        public IndexBuffer() {
            super(GL15.GL_ELEMENT_ARRAY_BUFFER);
        }

        public IndexBuffer(String debugName) {
            super(debugName, GL15.GL_ELEMENT_ARRAY_BUFFER);
        }
    }

    public static class VertexBuffer extends Buffer {
        public VertexBuffer() {
            super(GL15.GL_ARRAY_BUFFER);
        }

        public VertexBuffer(String debugName) {
            super(debugName, GL15.GL_ARRAY_BUFFER);
        }
    }

    private final int id;
    private final String debugName;
    private final int target;

    private int capacity = 0;
    private int size = 0;

    public Buffer(int target) {
        this("", target);
    }

    public Buffer(String debugName, int target) {
        Logger.tag("GL").info("Creating buffer {}", debugName);
        this.debugName = debugName;
        this.target = target;
        id = GL15.glGenBuffers();
    }

    public void destroy() {
        Logger.tag("GL").info("Destroying buffer {}", debugName);
        GL15.glDeleteBuffers(id);
    }

    public void bind() {
        bind(target);
    }

    public void bind(int target) {
        GL15.glBindBuffer(target, id);
    }

    public void unbind() {
        unbind(target);
    }

    public void unbind(int target) {
        GL15.glBindBuffer(target, 0);
    }

    public void allocate(int capacity) {
        if (this.capacity >= capacity) return;
        Logger.tag("GL").debug("Allocating {} bytes of memory for buffer {}", capacity, debugName);

        bind();
        GL15.glBufferData(target, capacity, GL15.GL_STATIC_DRAW);
        unbind();

        this.capacity = capacity;
    }

    public void store(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        store(buffer);
    }

    public void store(FloatBuffer data) {
        allocate(data.remaining() * Float.BYTES);

        bind();
        GL15.glBufferSubData(target, 0, data);
        unbind();

        this.size = data.remaining() * Float.BYTES;
    }

    public void store(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        store(buffer);
    }

    public void store(IntBuffer data) {
        allocate(data.remaining() * Integer.BYTES);

        bind();
        GL15.glBufferSubData(target, 0, data);
        unbind();

        this.size = data.remaining() * Float.BYTES;
    }

    public int getId() {
        return id;
    }

    public String getDebugName() {
        return debugName;
    }

    @Override
    public String toString() {
        return ("Buffer " + debugName).trim();
    }

    public int getTarget() {
        return target;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public Slice getSlice(int offset, int stride) {
        return new Slice(this, offset, stride);
    }

    public List<Slice> getSlices(BufferType... layout) {
        List<Slice> slices = new ArrayList<>(layout.length);

        int offset = 0;
        int stride = Arrays.stream(layout).map(BufferType::getStride).reduce(0, Integer::sum);

        for (BufferType attribute : layout) {
            slices.add(getSlice(offset, stride));
            offset += attribute.getStride();
        }

        return slices;
    }
}
