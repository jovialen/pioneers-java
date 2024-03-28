package com.github.jovialen.motor.render.resource.material;

import com.github.jovialen.motor.render.resource.shader.ShaderSource;
import com.github.jovialen.motor.render.resource.shader.ShaderUniforms;

import java.nio.file.Path;

public interface CustomMaterial {
    String SHADERS_DIR = Path.of("src", "main", "resources", "shaders").toString();

    void apply(ShaderUniforms shaderUniforms);
    ShaderSource getShader();
}
