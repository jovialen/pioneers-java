package com.github.jovialen.motor.render.pipeline;

public class SourceShader implements ShaderSource {
    private final String source;

    public SourceShader(String source) {
        this.source = source;
    }

    @Override
    public String getSource() {
        return source;
    }
}
