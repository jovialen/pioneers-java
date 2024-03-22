package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.renderer.SceneRenderer;

/**
 * Perform a single render.
 */
public class RenderTask extends SceneRendererTask {
    public RenderTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
    }

    @Override
    public void invoke() {
        sceneRenderer.render();
    }
}
