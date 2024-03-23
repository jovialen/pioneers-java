package com.github.jovialen.motor.core;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.window.Window;
import com.github.jovialen.motor.window.event.WindowCloseEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.checkerframework.checker.units.qual.C;
import org.lwjgl.glfw.GLFW;
import org.tinylog.Logger;

public abstract class Application {
    private final String name;
    private final EventBus eventBus;
    private final Window window;
    private final Clock clock;
    private final Scene initialScene;

    private boolean running = false;
    private SceneRoot sceneRoot;

    public Application(String name, Scene initialScene) {
        this.name = name;
        this.initialScene = initialScene;

        eventBus = new EventBus();
        window = new Window(eventBus, name);
        clock = new Clock();

        eventBus.register(this);
    }

    public void run() {
        open();
        clock.reset();
        while (running) {
            sceneRoot.process(clock.tick());
            GLFW.glfwWaitEvents();
        }
        close();
    }

    public String getName() {
        return name;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Window getWindow() {
        return window;
    }

    public Clock getClock() {
        return clock;
    }

    @Subscribe
    public void onWindowClose(WindowCloseEvent event) {
        if (event.window != window) return;
        Logger.tag("APP").debug("Window for app {} requested to close", name);
        running = false;
    }

    private void open() {
        Logger.tag("APP").info("Opening app {}", name);

        window.open();
        running = true;

        sceneRoot = initialScene.instantiate(new SceneRoot(this));
        sceneRoot.start();
    }

    private void close() {
        Logger.tag("APP").info("Closing app {}", name);

        sceneRoot.stop();

        window.close();
    }
}
