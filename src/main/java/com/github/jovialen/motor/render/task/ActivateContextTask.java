package com.github.jovialen.motor.render.task;

import com.github.jovialen.motor.render.Renderer;

public class ActivateContextTask extends RenderTask {
    public ActivateContextTask(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void invoke() {
        renderer.getContext().activate();
    }
}
