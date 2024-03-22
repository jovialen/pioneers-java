package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.scene.renderer.SceneRenderer;

/**
 * Synchronize the renderer with the given scene graph.
 */
public class RendererSynchronizationTask extends SceneRendererTask {
    private final SceneRoot root;

    public RendererSynchronizationTask(SceneRenderer sceneRenderer, SceneRoot root) {
        super(sceneRenderer);
        this.root = root;
    }

    @Override
    public void invoke() {
        sceneRenderer.sync(root);
    }
}
