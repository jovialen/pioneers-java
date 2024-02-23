package com.github.jovialen.motor.window;

import com.github.jovialen.motor.utils.GLFWBoolean;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

import java.util.List;

public class Window {
    private final EventBus eventBus;

    private long handle = MemoryUtil.NULL;
    private String title;
    private boolean visible = true;
    private GLFWBoolean resizable = new GLFWBoolean(true);
    private GLFWBoolean decorated = new GLFWBoolean(true);
    private Vector2i size = null;
    private Vector2i position = null;
    private Monitor monitor = null;

    public Window(EventBus eventBus, String title) {
        this.eventBus = eventBus;
        this.title = title;
    }

    public boolean open() {
        if (isOpen()) return true;
        Logger.info("Opening window {}", title);

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable.asGlfw());
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, decorated.asGlfw());

        if (size == null) {
            Logger.warn("No window size set. Selecting a fitting size");
            List<GLFWVidMode> videoModes = Monitor.getPrimary().getVideoModes();
            if (videoModes.isEmpty()) {
                Logger.warn("No video modes found. Selecting explicit starting size");
                size = new Vector2i(600, 400);
            } else {
                GLFWVidMode smallest = videoModes.getFirst();
                size = new Vector2i(smallest.width(), smallest.height());
            }
            Logger.debug("Selected window size is {}x{}", size.x, size.y);
        }

        Vector2i size = getSize();
        long monitor = MemoryUtil.NULL;

        if (this.monitor != null) {
            monitor = this.monitor.getHandle();
            GLFWVidMode videoMode = this.monitor.getVideoMode();
            GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, videoMode.redBits());
            GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, videoMode.greenBits());
            GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, videoMode.blueBits());
            GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, videoMode.refreshRate());
        }

        handle = GLFW.glfwCreateWindow(size.x, size.y, title, monitor, MemoryUtil.NULL);
        if (!isOpen()) {
            Logger.error("Failed to open window");
            return false;
        }

        if (position == null) {
            position = center();
        }
        setPosition(position);

        GLFW.glfwSetWindowCloseCallback(handle, (long win) -> {
            Logger.debug("Window {} has recieved a close request", title);
            eventBus.post(new WindowCloseEvent(this));
        });
        GLFW.glfwSetWindowSizeCallback(handle, (long win, int w, int h) -> {
            Logger.debug("Window {} resized to {}x{}", title, w, h);
            Vector2i newSize = new Vector2i(w, h);
            eventBus.post(new WindowSizeEvent(this, newSize, size));
            size.x = w;
            size.y = h;
        });
        GLFW.glfwSetWindowPosCallback(handle, (long win, int x, int y) -> {
            position.x = x;
            position.y = y;
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

    public void setDecorated(boolean decorated) {
        this.decorated.set(decorated);
        if (isOpen()) {
            GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_DECORATED, this.decorated.asGlfw());
        }
    }

    public boolean isDecorated() {
        return decorated.get();
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

    public void setSize(Vector2i size) {
        this.size = size;
        if (!isOpen()) return;

        if (isFullscreen()) {
            Logger.warn("Setting size for fullscreen window. Change video mode instead");
            return;
        }

        GLFW.glfwSetWindowSize(handle, size.x, size.y);
    }

    public Vector2i getSize() {
        if (monitor != null) {
            return monitor.getResolution();
        }
        return size;
    }

    public void setMonitor(Monitor monitor) {
        setMonitor(monitor, monitor != null ? monitor.getVideoMode() : null);
    }

    public void setMonitor(Monitor monitor, GLFWVidMode videoMode) {
        this.monitor = monitor;
        if (!isOpen()) return;

        if (monitor != null) {
            GLFW.glfwSetWindowMonitor(handle, monitor.getHandle(),
                    0, 0,
                    videoMode.width(), videoMode.height(),
                    videoMode.refreshRate());
        } else {
            GLFW.glfwSetWindowMonitor(handle, MemoryUtil.NULL,
                    position.x, position.y,
                    size.x, size.y,
                    0);
        }
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public boolean shouldClose() {
        return !isOpen() || GLFW.glfwWindowShouldClose(handle);
    }

    public void setFullscreen(boolean fullscreen) {
        if (fullscreen && !isFullscreen()) {
            setMonitor(Monitor.getPrimary());
        } else if (!fullscreen && isFullscreen()) {
            setMonitor(null);
        }
    }

    public boolean isFullscreen() {
        return getMonitor() != null;
    }

    private Vector2i center() {
        return (Monitor.getPrimary().getResolution().sub(size)).div(2);
    }

    private void setPosition(Vector2i position) {
        if (isOpen() && monitor == null) {
            GLFW.glfwSetWindowPos(handle, position.x, position.y);
        }
    }
}
