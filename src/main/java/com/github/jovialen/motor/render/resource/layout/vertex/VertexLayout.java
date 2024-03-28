package com.github.jovialen.motor.render.resource.layout.vertex;

import com.github.jovialen.motor.render.resource.layout.DataType;

import java.util.ArrayList;
import java.util.List;

public class VertexLayout {
    private final List<BufferBinding> buffers;
    private final List<VertexAttribute> attributes;

    public VertexLayout() {
        buffers = new ArrayList<>();
        attributes = new ArrayList<>();
    }

    public VertexLayout(List<BufferBinding> buffers, List<VertexAttribute> attributes) {
        this.buffers = buffers;
        this.attributes = attributes;
    }

    public void addBuffer(int stride) {
        buffers.add(new BufferBinding(stride));
    }

    public void addBuffer(int offset, int stride) {
        buffers.add(new BufferBinding(offset, stride));
    }

    public void addBuffer(int offset, int stride, BufferInputRate inputRate) {
        buffers.add(new BufferBinding(offset, stride, inputRate));
    }

    public void addAttribute(String name, int bufferIndex, int offset, DataType dataType) {
        attributes.add(new VertexAttribute(name, bufferIndex, offset, dataType));
    }

    public List<BufferBinding> getBuffers() {
        return buffers;
    }

    public List<VertexAttribute> getAttributes() {
        return attributes;
    }
}
