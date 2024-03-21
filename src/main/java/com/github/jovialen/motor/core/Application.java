package com.github.jovialen.motor.core;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRenderer;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.scene.tasks.ActivateContextTask;
import com.github.jovialen.motor.scene.tasks.DeactivateContextTask;
import com.github.jovialen.motor.scene.tasks.RenderTask;
import com.github.jovialen.motor.scene.tasks.RendererSynchronizationTask;
import com.github.jovialen.motor.threads.JobThread;
import com.github.jovialen.motor.window.Window;
import com.github.jovialen.motor.window.events.WindowCloseEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;
import org.tinylog.Logger;

public abstract class Application {
    private final String name;
    private final boolean debug;

    private final EventBus eventBus = new EventBus();
    private final Window window;

    private boolean running = false;
    private JobThread renderThread;
    private SceneRenderer renderer;
    private SceneRoot scene;

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
        if (this.scene != null && running) {
            Logger.tag("APP").info("Unloading scene {}", this.scene);
            this.scene.stop();
        }

        Logger.tag("APP").info("Loading scene {}", scene);
        this.scene = scene.instantiate();

        if (running) {
            this.scene.start();
        }
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

        renderThread = new JobThread();
        renderThread.addTask(new ActivateContextTask(renderer));

        scene.start();
        renderThread.start();

        window.setVisible(true);
        running = true;
    }

    private void stop() {
        if (scene != null) {
            Logger.tag("APP").info("Unloading scene {}", scene);
            scene.stop();
        }

        window.setVisible(false);

        renderThread.addTask(new DeactivateContextTask(renderer));
        renderThread.waitIdle();
        renderThread.stopWorking();
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            Logger.tag("APP").error("Failed to join with the render thread: {}", e);
        }

        window.close();

        Logger.tag("APP").info("App {} finished running", name);
    }

    private void sync() {
        // Queue synchronization after render
        renderThread.addTask(new RendererSynchronizationTask(renderer, scene));

        // Wait for sync to be complete
        renderThread.waitIdle();

        // Poll events
        GLFW.glfwWaitEvents();
    }

    private void process() {
        // Start render
        renderThread.addTask(new RenderTask(renderer));

        // Update scene
        scene.preProcess();
        scene.process();
        scene.postProcess();
    }
}
