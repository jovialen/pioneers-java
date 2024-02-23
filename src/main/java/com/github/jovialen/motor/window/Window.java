package com.github.jovialen.motor.window;

import com.github.jovialen.motor.utils.GLFWBoolean;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

public class Window {
    private final EventBus eventBus;

    private long handle = MemoryUtil.NULL;
    private String title;
    private boolean visible = true;
    private GLFWBoolean resizable = new GLFWBoolean(true);
    private Vector2i size = new Vector2i(1280, 720);

    public Window(EventBus eventBus, String title) {
        this.eventBus = eventBus;
        this.title = title;
    }

    public boolean open() {
        if (isOpen()) return true;
        Logger.info("Opening window {}", title);

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable.asGlfw());

        handle = GLFW.glfwCreateWindow(size.x, size.y, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (!isOpen()) {
            Logger.error("Failed to open window");
            return false;
        }

        GLFW.glfwSetWindowCloseCallback(handle, (long win) -> {
            Logger.debug("Window {} has recieved a close request", title);
            eventBus.post(new WindowCloseEvent(this));
        });
        GLFW.glfwSetWindowSizeCallback(handle, (long win, int w, int h) -> {
            Logger.debug("Window {} resized to {}x{}", title, w, h);
            Vector2i newSize = new Vector2i(w, h);
            eventBus.post(new WindowSizeEvent(this, newSize, size));
            size = newSize;
        });

        setVisible(visible);
        return true;
    }

    public void close() {
        if (!isOpen()) return;
        Logger.info("Closing window {}", title);
        GLFW.glfwDestroyWindow(handle);
        handle = MemoryUtil.NULL;
    }

    public boolean isOpen() {
        return handle != MemoryUtil.NULL;
    }

    public long getHandle() {
        return handle;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (!isOpen()) return;

        if (visible) {
            GLFW.glfwShowWindow(handle);
        } else {
            GLFW.glfwHideWindow(handle);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setResizable(boolean resizable) {
        this.resizable.set(resizable);
        if (isOpen()) {
            GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_RESIZABLE, this.resizable.asGlfw());
        }
    }

    public boolean isResizable() {
        return resizable.get();
    }

    public void setTitle(String title) {
        this.title = title;
        if (isOpen()) {
            GLFW.glfwSetWindowTitle(handle, title);
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean shouldClose() {
        return !isOpen() || GLFW.glfwWindowShouldClose(handle);
    }
}
