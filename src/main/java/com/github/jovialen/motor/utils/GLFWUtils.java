package com.github.jovialen.motor.utils;

import org.lwjgl.glfw.GLFW;

public class GLFWUtils {
    public static int bool(boolean bool) {
        return bool ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
    }

    public static boolean bool(int bool) {
        return bool != GLFW.GLFW_FALSE;
    }
}
