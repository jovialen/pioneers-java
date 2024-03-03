package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.gl.*;
import com.github.jovialen.motor.render.image.FileImage;
import com.github.jovialen.motor.render.image.NetImage;
import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.MeshBuffer;
import com.github.jovialen.motor.render.mesh.Vertex;
import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRenderer;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

public abstract class Application {
    public final String name;
    public final boolean debug;

    protected final EventBus eventBus = new EventBus();
    protected final Window window;

    private SceneNode scene;

    protected Application(String name) {
        this(name, false);
    }

    protected Application(String name, boolean debug) {
        this.name = name;
        this.debug = debug;
        window = new Window(eventBus, name, debug);
    }

    public void run() {
        Logger.tag("APP").info("Starting app {}", name);

        if (scene == null) {
            Logger.tag("APP").error("No scene is set");
        }

        window.setVisible(false);
        window.open();

        SceneRenderer renderer = new SceneRenderer(window.getGlContext());

        window.setVisible(true);
        while (window.isOpen() && !window.shouldClose() && scene != null && scene.hasChildren()) {
            scene.update();
            renderer.submit(scene);
            GLFW.glfwPollEvents();
        }

        window.close();

        Logger.tag("APP").info("App {} finished running", name);
    }

    protected void setScene(Scene scene) {
        Logger.tag("APP").info("Loading scene {}", scene);
        this.scene = scene.instantiate();
    }

    protected SceneNode getScene() {
        return scene;
    }
}
