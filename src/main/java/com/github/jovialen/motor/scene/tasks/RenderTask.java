package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;

public class RenderTask extends SceneRendererTask {
    public RenderTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
    }

    @Override
    public void invoke() {
        sceneRenderer.render();
    }
}
