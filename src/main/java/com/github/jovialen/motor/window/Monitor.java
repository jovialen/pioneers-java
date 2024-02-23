package com.github.jovialen.motor.window;

import org.joml.Vector2i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
    private final long monitor;

    public Monitor(long monitor) {
        this.monitor = monitor;
    }

    public long getHandle() {
        return monitor;
    }

    public GLFWVidMode getVideoMode() {
        return GLFW.glfwGetVideoMode(monitor);
    }

    public List<GLFWVidMode> getVideoModes() {
        GLFWVidMode.Buffer videoModes = GLFW.glfwGetVideoModes(monitor);
        if (videoModes == null) {
            Logger.error("Failed to find any native video modes for monitor");
            return new ArrayList<>();
        }
        return videoModes.stream().toList();
    }

    public Vector2i getResolution() {
        GLFWVidMode videoMode = getVideoMode();
        return new Vector2i(videoMode.width(), videoMode.height());
    }

    public static Monitor getPrimary() {
        return new Monitor(GLFW.glfwGetPrimaryMonitor());
    }

    public static List<Monitor> getMonitors() {
        List<Monitor> monitors = new ArrayList<>();
        PointerBuffer ids = GLFW.glfwGetMonitors();
        if (ids == null) {
            Logger.error("Failed to find any monitors");
            return monitors;
        }
        int count = ids.remaining();
        for (int i = 0; i < count; i++) {
            monitors.add(new Monitor(ids.get(i)));
        }
        return monitors;
    }
}
