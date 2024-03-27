package com.github.jovialen.motor.render.resource.layout;

public class VertexLayoutBuilder {
    private final VertexLayout vertexLayout = new VertexLayout();

    public VertexLayoutBuilder addBuffer() {
        vertexLayout.addBuffer(0, 0, BufferInputRate.PER_VERTEX);
        return this;
    }

    public VertexLayoutBuilder perVertex() {
        getBuffer().inputRate = BufferInputRate.PER_VERTEX;
        return this;
    }

    public VertexLayoutBuilder perInstance() {
        getBuffer().inputRate = BufferInputRate.PER_INSTANCE;
        return this;
    }

    public VertexLayoutBuilder setOffset(int offset) {
        getBuffer().offset = offset;
        return this;
    }

    public VertexLayoutBuilder addAttribute(String name, DataType dataType) {
        BufferBinding buffer = getBuffer();
        vertexLayout.addAttribute(name, getBufferIndex(), buffer.stride, dataType);
        buffer.stride += dataType.size();
        return this;
    }

    public VertexLayout build() {
        return vertexLayout;
    }

    private BufferBinding getBuffer() {
        return vertexLayout.getBuffers().getLast();
    }

    private int getBufferIndex() {
        if (vertexLayout.getBuffers().isEmpty()) addBuffer();
        return vertexLayout.getBuffers().size() - 1;
    }
}
