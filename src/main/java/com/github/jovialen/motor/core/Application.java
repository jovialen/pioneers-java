package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.*;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.file.Path;

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
        GLContext context = window.getGlContext();

        Buffer positionBuffer = new Buffer("Triangle Positions", GL15.GL_ARRAY_BUFFER);
        Buffer colorBuffer = new Buffer("Triangle Colors", GL15.GL_ARRAY_BUFFER);
        Buffer indexBuffer = new Buffer("Triangle Indices", GL15.GL_ELEMENT_ARRAY_BUFFER);
        BufferArray bufferArray = new BufferArray("Triangle");

        float[] positions = {
                0.0f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f,
        };
        positionBuffer.store(positions);

        float[] colors = {
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
        };
        colorBuffer.store(colors);

        int[] indices = {
                0, 1, 2
        };
        indexBuffer.store(indices);

        bufferArray.setIndexBuffer(indexBuffer);
        bufferArray.insert(0, positionBuffer, BufferType.FLOAT2);
        bufferArray.insert(1, colorBuffer, BufferType.FLOAT3);

        ShaderProgram program = new ShaderProgram("Triangle Program");
        String shadersDir = Path.of("src", "main", "resources", "shaders").toString();

        Shader vertexShader = new Shader(GL20.GL_VERTEX_SHADER);
        vertexShader.setSource(Path.of(shadersDir, "triangle.vs.glsl"));

        Shader fragmentShader = new Shader(GL20.GL_FRAGMENT_SHADER);
        fragmentShader.setSource(Path.of(shadersDir, "triangle.fs.glsl"));

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

        window.setVisible(true);
        while (window.isOpen() && !window.shouldClose()) {
            Vector2i size = window.getSize();
            GL11.glViewport(0, 0, size.x, size.y);

            GL11.glClearColor(1, 1, 1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            program.use();
            bufferArray.bind();
            GL11.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_INT, 0);

            window.present();
            GLFW.glfwWaitEvents();
        }
        window.setVisible(false);

        program.destroy();

        bufferArray.destroy();
        positionBuffer.destroy();
        colorBuffer.destroy();
        indexBuffer.destroy();

        window.close();
    }
}
