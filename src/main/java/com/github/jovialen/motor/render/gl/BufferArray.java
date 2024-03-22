package com.github.jovialen.motor.render.gl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.tinylog.Logger;

public class BufferArray {
    private final int id;
    private final String debugName;

    public BufferArray() {
        this("");
    }

    public BufferArray(String debugName) {
        Logger.tag("GL").info("Creating buffer array {}", debugName);
        this.debugName = debugName;
        id = GL30.glGenVertexArrays();
    }

    public void destroy() {
        Logger.tag("GL").info("Destroying buffer array {}", debugName);

        GL30.glDeleteVertexArrays(id);
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void setIndexBuffer(Buffer indexBuffer) {
        Logger.tag("GL").debug("Setting index buffer of {} to {}", this, indexBuffer);

        if (indexBuffer.getTarget() != GL20.GL_ELEMENT_ARRAY_BUFFER) {
            Logger.tag("GL").warn("Buffer is not a vertex buffer. Proceeding with bind as vertex buffer");
        }

        bind();
        indexBuffer.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
        unbind();
    }

    public void insert(int index, Buffer buffer, BufferType type) {
        insert(index, buffer.getSlice(0, type.getStride()), type);
    }

    public void insert(int index, Buffer.Slice slice, BufferType type) {
        Logger.tag("GL").debug("Binding {} to {} at index {} with type {} (offset: {}, stride: {})",
                slice.buffer, this, index, type, slice.offset, slice.stride);

        if (slice.buffer.getTarget() != GL20.GL_ARRAY_BUFFER) {
            Logger.tag("GL").warn("Buffer is not a vertex buffer. Proceeding with bind as vertex buffer");
        }

        bind();
        slice.buffer.bind(GL20.GL_ARRAY_BUFFER);

        GL20.glEnableVertexAttribArray(index);
        GL20.glVertexAttribPointer(index,
                type.getComponentCount(),
                type.getGlType(),
                false,
                slice.stride,
                slice.offset);

        slice.buffer.unbind(GL20.GL_ARRAY_BUFFER);
        unbind();
    }

    public void remove(int index) {
        Logger.tag("GL").debug("Removing {} index {}", this, index);
        bind();
        GL20.glDisableVertexAttribArray(index);
        unbind();
    }

    public int getId() {
        return id;
    }

    public String getDebugName() {
        return debugName;
    }

    @Override
    public String toString() {
        return ("Buffer Array " + debugName).trim();
    }
}
