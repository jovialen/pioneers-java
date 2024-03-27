package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.layout.VertexLayout;
import com.github.jovialen.motor.render.resource.mesh.Vertex;

import java.util.List;

public interface ShaderSource {
    List<ShaderModuleSource> getShaderModules();

    default VertexLayout getLayout() {
        return Vertex.LAYOUT;
    }
}
