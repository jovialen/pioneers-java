package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.gl.*;
import com.github.jovialen.motor.render.image.FileImage;
import com.github.jovialen.motor.render.image.NetImage;
import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.Vertex;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

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
        window.setVisible(false);
        window.open();

        Mesh mesh = new Mesh("Quad Mesh");
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector2f(0.0f, 1.0f)));
        mesh.vertices.add(new Vertex(new Vector3f(0.5f,  0.5f, 0.0f), new Vector2f(1.0f, 1.0f)));
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector2f(0.0f, 0.0f)));
        mesh.vertices.add(new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector2f(1.0f, 0.0f)));
        mesh.indices = Arrays.asList(0, 2, 3, 1, 0, 3);

        BufferArray bufferArray = mesh.build();

        ShaderProgram program = new ShaderProgram("Quad Program");
        String shadersDir = Path.of("src", "main", "resources", "shaders").toString();

        Shader vertexShader = new Shader(GL20.GL_VERTEX_SHADER);
        vertexShader.setSource(Path.of(shadersDir, "quad.vs.glsl"));

        Shader fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER);
        fragmentShader.setSource(Path.of(shadersDir, "quad.fs.glsl"));

        if (!fragmentShader.compile() || !vertexShader.compile()) {
            window.close();
            return;
        }

        program.attach(vertexShader, fragmentShader);

        if (!program.finish()) {
            window.close();
            return;
        }

        vertexShader.destroy();
        fragmentShader.destroy();

        String texturesDir = Path.of("src", "main", "resources", "textures").toString();
        // FileImage openglImage = new FileImage("OpenGL Logo", Path.of(texturesDir, "opengl-logo.png"));
        NetImage openglImage = NetImage.create("https://cdn.freebiesupply.com/logos/large/2x/opengl-1-logo-png-transparent.png");
        if (openglImage == null) {
            window.close();
            return;
        }
        Texture2D texture = openglImage.build();

        window.setVisible(true);
        while (window.isOpen() && !window.shouldClose()) {
            Vector2i size = window.getSize();
            GL11.glViewport(0, 0, size.x, size.y);

            GL11.glClearColor(1, 1, 1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            program.use();
            bufferArray.bind();
            texture.bind();
            GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);

            window.present();
            GLFW.glfwWaitEvents();
        }
        window.setVisible(false);

        bufferArray.destroy();
        texture.destroy();
        program.destroy();

        window.close();
    }
}
