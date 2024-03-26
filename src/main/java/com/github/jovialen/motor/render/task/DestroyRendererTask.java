package com.github.jovialen.motor.render.task;

import com.github.jovialen.motor.render.Renderer;

public class DestroyRendererTask extends RenderTask {
    public DestroyRendererTask(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void invoke() {
        renderer.destroy();
    }
}
