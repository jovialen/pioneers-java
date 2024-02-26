package com.github.jovialen.pioneers;

import com.github.jovialen.motor.Motor;
import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.window.Monitor;
import com.github.jovialen.motor.window.WindowSizeEvent;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.stb.STBImage;
import org.tinylog.Logger;

public class Pioneers extends Application {
    public static final String NAME = "Pioneers";

    public Pioneers() {
        super(NAME);
        window.setFullscreen(false);
    }

    public static void main(String[] args) {
        Motor.init();

        Pioneers app = new Pioneers();
        app.run();

        Motor.shutdown();
    }
}
