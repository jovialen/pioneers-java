package com.github.jovialen.motor.core;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRenderer;
import com.github.jovialen.motor.window.Window;
import com.github.jovialen.motor.window.events.WindowCloseEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;
import org.tinylog.Logger;

import java.time.Duration;

public abstract class Application {
    private final String name;
    private final boolean debug;

    private final EventBus eventBus = new EventBus();
    private final Window window;

    private boolean running = false;
    private SceneRenderer renderer;
    private SceneNode scene;

    protected Application(String name) {
        this(name, false);
    }

    protected Application(String name, boolean debug) {
        this.name = name;
        this.debug = debug;

        window = new Window(eventBus, name, debug);

        eventBus.register(this);
    }

    public void run() {
        start();
        while (running) {
            sync();
            process();
        }
        stop();
    }

    @Subscribe
    public void onWindowClose(WindowCloseEvent event) {
        running = false;
    }

    public void setScene(Scene scene) {
        Logger.tag("APP").info("Loading scene {}", scene);
        this.scene = scene.instantiate();
    }

    public SceneNode getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public boolean isDebug() {
        return debug;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Window getWindow() {
        return window;
    }

    public boolean isRunning() {
        return running;
    }

    private void start() {
        Logger.tag("APP").info("Starting app {}", name);

        if (scene == null) {
            Logger.tag("APP").error("No scene is set");
            return;
        }

        window.setVisible(false);
        if (!window.open()) {
            Logger.tag("APP").error("Failed to open window");
            return;
        }

        renderer = new SceneRenderer(window.getGlContext());

        window.setVisible(true);
        running = true;
    }

    private void stop() {
        window.setVisible(false);

        renderer.destroy();

        window.close();

        Logger.tag("APP").info("App {} finished running", name);
    }

    private void sync() {
        // Synchronize renderer with scene
        GLFW.glfwWaitEvents();
        renderer.sync(scene);
    }

    private void process() {
        // Update scene
        scene.preProcess();
        scene.process();
        scene.postProcess();

        // Start render
        renderer.render();
    }
}
