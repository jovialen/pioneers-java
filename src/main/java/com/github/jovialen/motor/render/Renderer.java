package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.context.GLContext;

public class Renderer {
    private final GLContext context;

    public Renderer(GLContext context) {
        this.context = context;
    }

    public GLContext getContext() {
        return context;
    }
}
