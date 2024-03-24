package com.github.jovialen.motor.render.task;

import com.github.jovialen.motor.render.Renderer;
import com.github.jovialen.motor.thread.ThreadTask;

public abstract class RenderTask implements ThreadTask {
    protected final Renderer renderer;

    public RenderTask(Renderer renderer) {
        this.renderer = renderer;
    }
}
