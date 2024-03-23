package com.github.jovialen.motor.window;

import com.github.jovialen.motor.input.event.key.InputCharEvent;
import com.github.jovialen.motor.input.event.key.InputKeyPressedEvent;
import com.github.jovialen.motor.input.event.key.InputKeyReleasedEvent;
import com.github.jovialen.motor.input.event.mouse.InputMouseEnterEvent;
import com.github.jovialen.motor.input.event.mouse.InputMouseMoveEvent;
import com.github.jovialen.motor.input.event.mouse.InputMousePressedButtonEvent;
import com.github.jovialen.motor.input.event.mouse.InputMouseReleasedButtonEvent;
import com.github.jovialen.motor.render.Surface;
import com.github.jovialen.motor.utils.MonitorUtils;
import com.github.jovialen.motor.window.event.WindowCloseEvent;
import com.github.jovialen.motor.window.event.WindowFocusEvent;
import com.github.jovialen.motor.window.event.WindowSizeEvent;
import com.google.common.eventbus.EventBus;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

public class Window implements Surface {
    private final EventBus eventBus;

    private String name;
    private Vector2i size;

    private long handle = MemoryUtil.NULL;

    public Window(EventBus eventBus, String name) {
        this.eventBus = eventBus;
        this.name = name;
    }

    public void open() {
        if (isOpen()) return;
        Logger.tag("WINDOW").info("Opening window {}", name);

        if (size == null) {
            size = MonitorUtils.getGuaranteedFitWindowSize();
            Logger.tag("WINDOW").warn("No window size set. Selecting size {} automatically", size);
        }

        handle = GLFW.glfwCreateWindow(size.x, size.y, name, MemoryUtil.NULL, MemoryUtil.NULL);

        configureEvents();
    }

    public void close() {
        if (!isOpen()) return;
        Logger.tag("WINDOW").info("Closing window {}", name);

        GLFW.glfwDestroyWindow(handle);
        handle = MemoryUtil.NULL;
    }

    public boolean isOpen() {
        return handle != MemoryUtil.NULL;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Logger.tag("WINDOW").debug("Renaming window {} to {}", this.name, name);
        this.name = name;

        if (isOpen()) {
            GLFW.glfwSetWindowTitle(handle, name);
        }
    }

    public Vector2i getSize() {
        return size;
    }

    public void setSize(Vector2i size) {
        Logger.tag("WINDOW").debug("Setting window {} size to {}", name, size);
        this.size = size;

        if (isOpen()) {
            GLFW.glfwSetWindowSize(handle, size.x, size.y);
        }
    }

    @Override
    public Vector2i getResolution() {
        return getSize();
    }

    public long getHandle() {
        return handle;
    }

    private void configureEvents() {
        GLFW.glfwSetWindowSizeCallback(handle, (window, width, height) -> {
            eventBus.post(new WindowSizeEvent(this, new Vector2i(width, height)));
        });
        GLFW.glfwSetWindowCloseCallback(handle, (window) -> {
            eventBus.post(new WindowCloseEvent(this));
        });
        GLFW.glfwSetWindowFocusCallback(handle, (window, focused) -> {
            eventBus.post(new WindowFocusEvent(this, focused));
        });
        GLFW.glfwSetCharCallback(handle, (window, codepoint) -> {
            eventBus.post(new InputCharEvent(this, codepoint));
        });
        GLFW.glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_RELEASE) {
                eventBus.post(new InputKeyReleasedEvent(this, key, scancode, mods));
            } else {
                boolean repeat = action == GLFW.GLFW_REPEAT;
                eventBus.post(new InputKeyPressedEvent(this, key, scancode, mods, repeat));
            }
        });
        GLFW.glfwSetMouseButtonCallback(handle, (window, button, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                eventBus.post(new InputMousePressedButtonEvent(this, button, mods));
            } else {
                eventBus.post(new InputMouseReleasedButtonEvent(this, button, mods));
            }
        });
        GLFW.glfwSetCursorPosCallback(handle, (window, x, y) -> {
            eventBus.post(new InputMouseMoveEvent(this, new Vector2d(x, y)));
        });
        GLFW.glfwSetCursorEnterCallback(handle, (window, entered) -> {
            eventBus.post(new InputMouseEnterEvent(this, entered));
        });
    }
}
