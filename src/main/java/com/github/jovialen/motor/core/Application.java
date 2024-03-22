package com.github.jovialen.motor.core;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.scene.renderer.SceneRenderer;
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
        if (event.window != window) return;
        running = false;
    }

    public void setScene(Scene scene) {
        Logger.tag("APP").info("Loading scene {}", scene);

        if (!running) {
            this.scene = scene.instantiate(this);
            return;
        }

        unloadScene();

        this.scene = scene.instantiate(this);
        this.scene.start();
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

    private void unloadScene() {
        if (scene == null) return;
        Logger.tag("APP").info("Unloading scene {}", this.scene);
        stopNodeAndChildren(scene);
    }

    private void start() {
        Logger.tag("APP").info("Starting app {}", name);

        if (scene == null) {
            Logger.tag("APP").error("No scene is set");
            return;
        }

        // Open the window
        window.setVisible(false);
        if (!window.open()) {
            Logger.tag("APP").error("Failed to open window");
            return;
        }

        // Set up the renderer
        renderer = new SceneRenderer(window.getGlContext());

        renderThread = new JobThread();
        renderThread.addTask(new ActivateContextTask(renderer));

        // Start the scene
        scene.start();
        renderThread.start();

        // Finalize
        window.setVisible(true);
        running = true;
    }

    private void stop() {
        window.setVisible(false);

        // Stop the render thread
        renderThread.addTask(new DeactivateContextTask(renderer));
        renderThread.waitIdle();
        renderThread.stopWorking();
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            Logger.tag("APP").error("Failed to join with the render thread: {}", e);
        }
        renderThread = null;

        renderer.destroy();

        // Unload the scene
        unloadScene();

        // Close
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

    private void stopNodeAndChildren(SceneNode node) {
        // No need to worry about the render thread if its disabled
        if (renderThread == null) {
            renderer.getContext().activate();
            node.stop();
            renderer.getContext().deactivate();
            return;
        }

        // Take ownership of the render context on this thread
        renderThread.addTask(new DeactivateContextTask(renderer));
        renderThread.waitIdle();
        renderer.getContext().activate();

        // Stop the node and its children
        node.stop();
        renderer.invalidate();

        // Give ownership back to the render thread
        renderer.getContext().deactivate();
        renderThread.addTask(new ActivateContextTask(renderer));
    }
}
