package com.github.jovialen.motor.core;

import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.lwjgl.glfw.GLFW;

public abstract class Application {
    public final String name;

    protected final EventBus eventBus = new EventBus();
    protected final Window window;

    protected Application(String name) {
        this.name = name;
        window = new Window(eventBus, name);
    }

    public void run() {
        window.open();
        while (window.isOpen() && !window.shouldClose()) {
            GLFW.glfwWaitEvents();
        }
        window.close();
    }
}
