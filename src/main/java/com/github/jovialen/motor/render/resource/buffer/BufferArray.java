package com.github.jovialen.motor.render.resource.buffer;

import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.DestructibleResource;
import com.github.jovialen.motor.render.resource.layout.BufferBinding;
import com.github.jovialen.motor.render.resource.layout.VertexAttribute;
import com.github.jovialen.motor.render.resource.layout.VertexLayout;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BufferArray implements DestructibleResource {
    private final int id;
    private final VertexLayout vertexLayout;
    private final Map<Integer, VertexBuffer> vertexBuffers = new HashMap<>();
    private IndexBuffer indexBuffer;

    public BufferArray(VertexLayout vertexLayout) {
        this(GL30.glGenVertexArrays(), vertexLayout);
    }

    public BufferArray(int id, VertexLayout vertexLayout) {
        Logger.tag("GL").info("Creating buffer array {}", id);
        this.id = id;
        this.vertexLayout = vertexLayout;
    }

    @Override
    public void destroy() {
        Logger.tag("GL").info("Destroying buffer array {}", id);
        GL30.glDeleteVertexArrays(id);
    }

    public void setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
        try (GLState gl = GLState.pushSharedState()) {
            gl.bindBufferArray(id);
            gl.bindIndexBuffer(indexBuffer.getId());
        }
    }

    public void setVertexBuffer(int index, VertexBuffer vertexBuffer) {
        vertexBuffers.put(index, vertexBuffer);

        BufferBinding bufferBinding = vertexLayout.getBuffers().get(index);
        List<VertexAttribute> attributes = vertexLayout.getAttributes().stream()
                .filter((attr) -> attr.bufferIndex() == index)
                .toList();

        try (GLState glState = GLState.pushSharedState()) {
            glState.bindBufferArray(id);
            glState.bindVertexBuffer(vertexBuffer.getId());

            Logger.tag("GL").warn("Binding vertex attributes directly in buffer array. " +
                    "Should instead use attribute names with shader program.");

            int i = 0;
            for (VertexAttribute attribute : attributes) {
                GL20.glVertexAttribPointer(i,
                        attribute.dataType().componentCount(),
                        attribute.dataType().glDataType(),
                        false,
                        bufferBinding.stride,
                        bufferBinding.offset + attribute.offset());
                GL20.glEnableVertexAttribArray(i);
                i++;
            }
        }
    }

    public int getId() {
        return id;
    }

    public VertexLayout getVertexLayout() {
        return vertexLayout;
    }

    public Map<Integer, VertexBuffer> getVertexBuffers() {
        return vertexBuffers;
    }

    public VertexBuffer getVertexBuffer(int index) {
        return vertexBuffers.get(index);
    }

    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }
}
