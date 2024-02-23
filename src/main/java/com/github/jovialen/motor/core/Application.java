package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.GLContext;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public abstract class Application {
    public final String name;
    public final boolean debug;

    protected final EventBus eventBus = new EventBus();
    protected final Window window;

    protected Application(String name) {
        this(name, false);
    }

    protected Application(String name, boolean debug) {
        this.name = name;
        this.debug = debug;
        window = new Window(eventBus, name, debug);
    }

    public void run() {
        window.open();
        GLContext context = window.getGlContext();
        while (window.isOpen() && !window.shouldClose()) {
            GLFW.glfwWaitEvents();
            GL11.glClearColor(1, 0, 1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            window.present();
        }
        window.close();
    }
}
