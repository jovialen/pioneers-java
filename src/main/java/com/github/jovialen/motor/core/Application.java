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
import java.util.List;

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

        Buffer vertexBuffer = new Buffer("Quad Vertices", GL15.GL_ARRAY_BUFFER);
        Buffer indexBuffer = new Buffer("Quad Indices", GL15.GL_ELEMENT_ARRAY_BUFFER);
        BufferArray bufferArray = new BufferArray("Quad");

        float[] positions = {
                -0.5f,  0.5f, 1.0f, 0.0f, 1.0f,
                 0.5f,  0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
        };
        vertexBuffer.store(positions);

        int[] indices = {
                0, 2, 3,
                1, 0, 3,
        };
        indexBuffer.store(indices);

        bufferArray.setIndexBuffer(indexBuffer);
        List<Buffer.Slice> slices = vertexBuffer.getSlices(BufferType.FLOAT2, BufferType.FLOAT3);
        bufferArray.insert(0, slices.get(0), BufferType.FLOAT2);
        bufferArray.insert(1, slices.get(1), BufferType.FLOAT3);

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

        window.setVisible(true);
        while (window.isOpen() && !window.shouldClose()) {
            Vector2i size = window.getSize();
            GL11.glViewport(0, 0, size.x, size.y);

            GL11.glClearColor(1, 1, 1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            program.use();
            bufferArray.bind();
            GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);

            window.present();
            GLFW.glfwWaitEvents();
        }
        window.setVisible(false);

        program.destroy();

        bufferArray.destroy();
        vertexBuffer.destroy();
        indexBuffer.destroy();

        window.close();
    }
}
