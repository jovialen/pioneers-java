package com.github.jovialen.motor.render.resource.layout.vertex;

import com.github.jovialen.motor.render.resource.layout.DataType;

public record VertexAttribute(String name, int bufferIndex, int offset, DataType dataType) {
}
