package com.github.jovialen.motor.core;

import org.lwjgl.glfw.GLFW;

public class Clock {
    private double startTime;
    private double lastTime;
    private double deltaTime;

    public Clock() {
        reset();
    }

    public void reset() {
        startTime = GLFW.glfwGetTime();
        lastTime = startTime;
    }

    public double tick() {
        double time = getTime();
        deltaTime = time - lastTime;
        lastTime = time;
        return deltaTime;
    }

    public double getTime() {
        return GLFW.glfwGetTime();
    }

    public double getStartTime() {
        return startTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getTimeSince(double moment) {
        return getTime() - moment;
    }

    public double elapsed() {
        return getTimeSince(startTime);
    }
}
