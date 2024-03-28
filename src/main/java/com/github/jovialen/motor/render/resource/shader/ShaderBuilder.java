package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.layout.uniform.UniformLayout;
import com.github.jovialen.motor.render.resource.layout.vertex.VertexLayout;
import com.github.jovialen.motor.render.resource.mesh.Vertex;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ShaderBuilder implements ShaderSource {
    private ShaderModuleSource vertexShader;
    private ShaderModuleSource fragmentShader;
    private VertexLayout vertexLayout = Vertex.LAYOUT;
    private UniformLayout uniformLayout = UniformLayout.EMPTY;

    public ShaderBuilder addVertexShader(Path path) {
        vertexShader = new ShaderModuleSource(readFile(path), ShaderStage.VERTEX);
        return this;
    }

    public ShaderBuilder addFragmentShader(Path path) {
        fragmentShader = new ShaderModuleSource(readFile(path), ShaderStage.FRAGMENT);
        return this;
    }

    public ShaderBuilder setVertexLayout(VertexLayout vertexLayout) {
        this.vertexLayout = vertexLayout;
        return this;
    }

    public ShaderBuilder setUniforms(UniformLayout uniformLayout) {
        this.uniformLayout = uniformLayout;
        return this;
    }

    @Override
    public List<ShaderModuleSource> getShaderModules() {
        return Stream.of(vertexShader, fragmentShader)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public VertexLayout getVertexLayout() {
        return vertexLayout;
    }

    @Override
    public UniformLayout getUniformLayout() {
        return uniformLayout;
    }

    private String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Logger.tag("RENDER").error("Failed to read shader module source: {}", e);
            return "";
        }
    }
}
