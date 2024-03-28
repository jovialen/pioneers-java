package com.github.jovialen.motor.render.resource.material;

import com.github.jovialen.motor.render.resource.layout.DataType;
import com.github.jovialen.motor.render.resource.layout.uniform.UniformLayoutBuilder;
import com.github.jovialen.motor.render.resource.mesh.Vertex;
import com.github.jovialen.motor.render.resource.shader.ShaderBuilder;
import com.github.jovialen.motor.render.resource.shader.ShaderSource;
import com.github.jovialen.motor.render.resource.shader.ShaderUniforms;
import org.joml.Vector4f;

import java.nio.file.Path;

public class DefaultMaterial implements CustomMaterial {
    public static final ShaderSource SHADER = new ShaderBuilder()
            .addVertexShader(Path.of(CustomMaterial.SHADERS_DIR, "default.vs.glsl"))
            .addFragmentShader(Path.of(CustomMaterial.SHADERS_DIR, "default.fs.glsl"))
            .setVertexLayout(Vertex.LAYOUT)
            .setUniforms(new UniformLayoutBuilder()
                    .addUniform("uColor", DataType.FLOAT4)
                    .addUniform("uModel", DataType.MAT4)
                    .addUniform("uView", DataType.MAT4)
                    .addUniform("uProjection", DataType.MAT4)
                    .build()
            );

    public Vector4f color = new Vector4f(1);

    @Override
    public void apply(ShaderUniforms shaderUniforms) {
        shaderUniforms.setUniform("uColor", color);
    }

    @Override
    public ShaderSource getShader() {
        return SHADER;
    }
}
