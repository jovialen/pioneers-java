package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.context.GLContext;
import org.tinylog.Logger;

public class Renderer {
    private final GLContext context;

    public Renderer(GLContext context) {
        Logger.tag("RENDER").info("Creating renderer");
        this.context = context;
    }

    public void destroy() {
        Logger.tag("RENDER").info("Destroying renderer");
    }

    public GLContext getContext() {
        return context;
    }
}
