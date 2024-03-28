package com.github.jovialen.motor.render.resource.layout.uniform;

import com.github.jovialen.motor.render.resource.layout.DataType;

public class UniformLayoutBuilder {
    private final UniformLayout layout = new UniformLayout();

    public UniformLayoutBuilder addUniform(String name, DataType dataType) {
        layout.addUniform(name, dataType);
        return this;
    }

    public UniformLayout build() {
        return layout;
    }
}
