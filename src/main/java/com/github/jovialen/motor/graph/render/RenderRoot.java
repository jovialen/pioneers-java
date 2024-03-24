package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class RenderRoot extends RenderNode {
    private final Application application;

    public RenderRoot(Application application) {
        super(null);
        this.application = application;
    }

    @Override
    public void run() {
        Vector2i resolution = getWindow().getResolution();
        GL11.glViewport(0, 0, resolution.x, resolution.y);
        GL11.glClearColor(1, 0, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        getWindow().present();
        super.run();
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
}
