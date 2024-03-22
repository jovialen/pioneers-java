package com.github.jovialen.motor.render.pipeline;

import com.github.jovialen.motor.render.gl.Shader;
import com.github.jovialen.motor.render.gl.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class Pipeline {
    public static final Pipeline DEFAULT = new Pipeline(null, null);

    private final ShaderSource vertexShader;
    private final ShaderSource fragmentShader;

    private boolean blending = false;
    private int blendSrc = GL20.GL_SRC_ALPHA;
    private int blendDst = GL20.GL_ONE_MINUS_SRC_ALPHA;

    public Pipeline(ShaderSource vertexShader, ShaderSource fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    public boolean useDefaultShaderProgram() {
        return vertexShader == null && fragmentShader == null;
    }

    public ShaderProgram build() {
        ShaderProgram program = new ShaderProgram();

        Shader vertexShader = new Shader("", GL20.GL_VERTEX_SHADER, this.vertexShader.getSource());
        Shader fragmentShader = new Shader("", GL20.GL_FRAGMENT_SHADER, this.fragmentShader.getSource());

        program.attach(vertexShader, fragmentShader);
        program.finish();

        vertexShader.destroy();
        fragmentShader.destroy();

        return program;
    }

    public boolean isBlending() {
        return blending;
    }

    public void setBlending(boolean blending) {
        this.blending = blending;
    }

    public void setBlendFunc(int src, int dst) {
        blendSrc = src;
        blendDst = dst;
    }

    public int getBlendSrc() {
        return blendSrc;
    }

    public int getBlendDst() {
        return blendDst;
    }
}
