package com.github.jovialen.motor.core;

import com.github.jovialen.motor.render.Buffer;
import com.github.jovialen.motor.render.BufferArray;
import com.github.jovialen.motor.render.BufferType;
import com.github.jovialen.motor.render.GLContext;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

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
        window.open();
        GLContext context = window.getGlContext();

        Buffer vertexBuffer = new Buffer("Triangle Vertices", GL15.GL_ARRAY_BUFFER);
        Buffer indexBuffer = new Buffer("Triangle Indices", GL15.GL_ELEMENT_ARRAY_BUFFER);
        BufferArray bufferArray = new BufferArray("Triangle");

        float[] vertices = {
                0.0f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f,
        };
        vertexBuffer.store(vertices);

        int[] indices = {
                0, 1, 2
        };
        indexBuffer.store(indices);

        bufferArray.setIndexBuffer(indexBuffer);
        bufferArray.insert(0, vertexBuffer, BufferType.FLOAT2);

        while (window.isOpen() && !window.shouldClose()) {
            Vector2i size = window.getSize();
            GL11.glViewport(0, 0, size.x, size.y);

            GL11.glClearColor(1, 0, 1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            bufferArray.bind();
            GL11.glDrawElements(GL11.GL_TRIANGLES, 3, GL11.GL_UNSIGNED_INT, 0);

            window.present();
            GLFW.glfwWaitEvents();
        }

        bufferArray.destroy();
        vertexBuffer.destroy();
        indexBuffer.destroy();

        window.close();
    }
}
