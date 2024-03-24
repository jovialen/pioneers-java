package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.RenderThread;
import com.github.jovialen.motor.render.Renderer;
import com.github.jovialen.motor.window.Window;
import com.github.jovialen.motor.window.event.WindowCloseEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;
import org.tinylog.Logger;

public abstract class Application {
    private final String name;
    private final EventBus eventBus;
    private final Window window;
    private final Clock clock;
    private final SceneSource initialScene;

    private boolean running = false;
    private Scene scene;
    private Renderer renderer;
    private RenderThread renderThread;

    public Application(String name, SceneSource initialScene, boolean debug) {
        this.name = name;
        this.initialScene = initialScene;

        eventBus = new EventBus();
        window = new Window(eventBus, name, debug);
        clock = new Clock();

        eventBus.register(this);
    }

    public void run() {
        open();
        while (running) {
            scene.update(clock.tick());
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

    public Scene getScene() {
        return scene;
    }

    public void setScene(SceneSource scene) {
        Logger.tag("APP").info("Setting scene to {}", scene);

        if (!running) {
            Logger.tag("APP").error("Cannot change scene. Application is not running");
            return;
        }

        unloadScene();
        this.scene = new Scene(this, scene);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public RenderThread getRenderThread() {
        return renderThread;
    }

    @Subscribe
    public void onWindowClose(WindowCloseEvent event) {
        if (event.window != window) return;
        Logger.tag("APP").debug("Window for app {} requested to close", name);
        running = false;
    }

    private void open() {
        Logger.tag("APP").info("Opening app {}", name);

        clock.reset();

        window.setVisible(false);
        window.open();
        running = true;

        renderer = new Renderer(window.getContext());
        renderThread = new RenderThread(renderer);
        renderThread.start();

        scene = new Scene(this, initialScene);
        scene.start();

        window.setVisible(true);
    }

    private void close() {
        Logger.tag("APP").info("Closing app {}", name);

        window.setVisible(false);

        unloadScene();

        renderThread.stop();
        window.close();
    }

    private void unloadScene() {
        Logger.tag("APP").debug("Unloading scene {}", scene);

        scene.stop();
        scene = null;
    }
}
