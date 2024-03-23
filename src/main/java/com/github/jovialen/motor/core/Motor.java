package com.github.jovialen.motor.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.stb.STBImage;
import org.tinylog.Logger;

public class Motor {
    public static void init() {
        Thread.setDefaultUncaughtExceptionHandler(new TinyLogUncaughtExceptionHandler());

        GLFW.glfwSetErrorCallback(new TinyLogGlfwErrorCallback());
        GLFW.glfwInit();

        STBImage.stbi_set_flip_vertically_on_load(true);
    }

    public static void shutdown() {
        GLFW.glfwTerminate();
    }

    private static class TinyLogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Logger.error(e);
            System.exit(-1);
        }
    }

    private static class TinyLogGlfwErrorCallback extends GLFWErrorCallback {
        @Override
        public void invoke(int error, long description) {
            Logger.tag("GLFW").error("GLFW Error {}: {}", error, getDescription(description));
        }
    }
}
