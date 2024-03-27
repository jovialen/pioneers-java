package com.github.jovialen.motor.render.resource.buffer;

import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.DestructibleResource;
import com.github.jovialen.motor.render.resource.layout.BufferBinding;
import com.github.jovialen.motor.render.resource.layout.VertexAttribute;
import com.github.jovialen.motor.render.resource.layout.VertexLayout;
import com.github.jovialen.motor.render.resource.shader.ShaderProgram;
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
    private ShaderProgram specialised;

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
    }

    public void specialiseToShader(ShaderProgram shaderProgram) {
        if (specialised == shaderProgram) return;
        specialised = shaderProgram;

        try (GLState glState = GLState.pushSharedState()) {
            glState.bindBufferArray(id);
            for (int i = 0; i < vertexLayout.getBuffers().size(); i++) {
                BufferBinding binding = vertexLayout.getBuffers().get(i);
                if (!vertexBuffers.containsKey(i)) {
                    Logger.tag("GL").warn("Missing vertex buffer {} in buffer array {}", i, id);
                    continue;
                }
                glState.bindVertexBuffer(vertexBuffers.get(i).getId());

                int finalI = i;
                List<VertexAttribute> attributes = vertexLayout.getAttributes().stream()
                        .filter((attr) -> attr.bufferIndex() == finalI)
                        .toList();

                for (VertexAttribute attribute : attributes) {
                    int location = shaderProgram.getAttributeLocation(attribute.name());

                    if (location == -1) {
                        Logger.tag("GL").warn("Shader is missing layout attribute {}", attribute.name());
                        continue;
                    }

                    GL20.glEnableVertexAttribArray(location);
                    GL20.glVertexAttribPointer(location,
                            attribute.dataType().componentCount(),
                            attribute.dataType().glDataType(),
                            false,
                            binding.stride,
                            binding.offset + attribute.offset());
                }
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
