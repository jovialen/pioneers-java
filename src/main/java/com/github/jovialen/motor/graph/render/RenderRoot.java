package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.render.Renderer;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;

public class RenderRoot extends RenderNode {
    private final Application application;

    public RenderRoot(Application application) {
        super(null);
        this.application = application;
        this.root = this;
    }

    public Application getApplication() {
        return application;
    }

    public Window getWindow() {
        return application.getWindow();
    }

    public EventBus getEventBus() {
        return application.getEventBus();
    }

    public Renderer getRenderer() {
        return application.getRenderer();
    }
}
