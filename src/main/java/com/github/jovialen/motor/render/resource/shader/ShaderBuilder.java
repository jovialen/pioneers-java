package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.layout.VertexLayout;
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

    @Override
    public List<ShaderModuleSource> getShaderModules() {
        return Stream.of(vertexShader, fragmentShader)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public VertexLayout getLayout() {
        return vertexLayout;
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
