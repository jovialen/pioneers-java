package com.github.jovialen.motor.window;

import com.github.jovialen.motor.render.gl.GLContext;
import com.github.jovialen.motor.utils.GLFWBoolean;
import com.github.jovialen.motor.window.events.WindowCloseEvent;
import com.github.jovialen.motor.window.events.WindowSizeEvent;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

import java.util.List;

public class Window {
    private final EventBus eventBus;
    private final GLFWBoolean debug;

    private long handle = MemoryUtil.NULL;
    private String title;
    private boolean visible = true;
    private GLFWBoolean resizable = new GLFWBoolean(true);
    private GLFWBoolean decorated = new GLFWBoolean(true);
    private Vector2i size = null;
    private Vector2i position = null;
    private Monitor monitor = null;
    private GLContext glContext;

    public Window(EventBus eventBus, String title, boolean debug) {
        this.eventBus = eventBus;
        this.title = title;
        this.debug = new GLFWBoolean(debug);
    }

    public boolean open() {
        if (isOpen()) return true;
        Logger.tag("WINDOW").info("Opening window {}", title);

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable.asGlfw());
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, decorated.asGlfw());
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_API);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, GLContext.VERSION_MAJOR);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, GLContext.VERSION_MINOR);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_DEBUG, debug.asGlfw());
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        if (size == null) {
            Logger.tag("WINDOW").warn("No window size set. Selecting a fitting size");
            List<GLFWVidMode> videoModes = Monitor.getPrimary().getVideoModes();
            if (videoModes.isEmpty()) {
                Logger.tag("WINDOW").warn("No video modes found. Selecting explicit starting size");
                size = new Vector2i(600, 400);
            } else {
                GLFWVidMode smallest = videoModes.getFirst();
                size = new Vector2i(smallest.width(), smallest.height());
            }
            Logger.tag("WINDOW").debug("Selected window size is {}x{}", size.x, size.y);
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
            Logger.tag("WINDOW").error("Failed to open window");
            return false;
        }

        if (position == null) {
            position = center();
        }
        setPosition(position);

        GLFW.glfwSetWindowCloseCallback(handle, (long win) -> {
            Logger.tag("WINDOW").debug("Window {} has recieved a close request", title);
            eventBus.post(new WindowCloseEvent(this));
        });
        GLFW.glfwSetWindowSizeCallback(handle, (long win, int w, int h) -> {
            Logger.tag("WINDOW").debug("Window {} resized to {}x{}", title, w, h);
            Vector2i newSize = new Vector2i(w, h);
            eventBus.post(new WindowSizeEvent(this, newSize, size));
            size.x = w;
            size.y = h;
        });
        GLFW.glfwSetWindowPosCallback(handle, (long win, int x, int y) -> {
            position.x = x;
            position.y = y;
        });

        glContext = new GLContext(this);

        setVisible(visible);
        return true;
    }

    public void close() {
        if (!isOpen()) return;
        Logger.tag("WINDOW").info("Closing window {}", title);
        GLFW.glfwDestroyWindow(handle);
        handle = MemoryUtil.NULL;
        glContext = null;
    }

    public boolean isOpen() {
        return handle != MemoryUtil.NULL;
    }

    public long getHandle() {
        return handle;
    }

    public GLContext getGlContext() {
        return glContext;
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
            Logger.tag("WINDOW").warn("Setting size for fullscreen window. Change video mode instead");
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

    public boolean isDebug() {
        return debug.get();
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

    public void present() {
        GLFW.glfwSwapBuffers(handle);
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
