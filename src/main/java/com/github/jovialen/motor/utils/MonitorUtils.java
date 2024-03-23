package com.github.jovialen.motor.utils;

import com.github.jovialen.motor.window.Monitor;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWVidMode;
import org.tinylog.Logger;

import java.util.List;

public class MonitorUtils {
    public static Vector2i getGuaranteedFitWindowSize() {
        List<GLFWVidMode> videoModes = Monitor.getPrimary().getVideoModes();

        if (videoModes.isEmpty()) {
            Logger.tag("WINDOW").warn("No video modes found. Selecting explicit starting size");
            return new Vector2i(600, 400);
        }

        GLFWVidMode smallest = videoModes.getFirst();
        return new Vector2i(smallest.width(), smallest.height());
    }
}
