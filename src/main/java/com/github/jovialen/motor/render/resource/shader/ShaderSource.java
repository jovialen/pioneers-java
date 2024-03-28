package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.layout.uniform.UniformLayout;
import com.github.jovialen.motor.render.resource.layout.vertex.VertexLayout;
import com.github.jovialen.motor.render.resource.mesh.Vertex;

import java.util.List;

public interface ShaderSource {
    List<ShaderModuleSource> getShaderModules();

    default VertexLayout getVertexLayout() {
        return Vertex.LAYOUT;
    }
    default UniformLayout getUniformLayout() { return new UniformLayout(); }
}
