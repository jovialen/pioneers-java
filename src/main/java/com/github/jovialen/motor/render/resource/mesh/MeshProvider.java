package com.github.jovialen.motor.render.resource.mesh;

import com.github.jovialen.motor.render.resource.ResourceProvider;

public class MeshProvider implements ResourceProvider<MeshData, MeshBuffer> {
    @Override
    public MeshBuffer provide(MeshData key) {
        MeshBuffer buffer = new MeshBuffer();
        buffer.store(key);
        return buffer;
    }
}
