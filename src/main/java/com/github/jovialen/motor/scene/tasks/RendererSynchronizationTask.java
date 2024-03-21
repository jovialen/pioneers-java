package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRenderer;
import com.github.jovialen.motor.scene.SceneRoot;

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
