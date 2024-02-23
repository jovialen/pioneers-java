package com.github.jovialen.pioneers;

import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.window.WindowSizeEvent;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.tinylog.Logger;

public class Pioneers extends Application {
    public static final String NAME = "Pioneers";

    public Pioneers() {
        super(NAME);
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new TinyLogUncaughtExceptionHandler());

        GLFW.glfwSetErrorCallback(new TinyLogGlfwErrorCallback());
        GLFW.glfwInit();

        Pioneers app = new Pioneers();
        app.run();

        GLFW.glfwTerminate();
    }

    private static class TinyLogUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Logger.error(e);
        }
    }

    private static class TinyLogGlfwErrorCallback extends GLFWErrorCallback {
        @Override
        public void invoke(int error, long description) {
            Logger.error("GLFW Error {}: {}", error, getDescription(description));
        }
    }
}
