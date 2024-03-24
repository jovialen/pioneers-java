package com.github.jovialen.motor.render.task;

import com.github.jovialen.motor.render.Renderer;

public class DeactivateContextTask extends RenderTask {
    public DeactivateContextTask(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void invoke() {
        renderer.getContext().deactivate();
    }
}
