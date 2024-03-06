package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRenderer;

public class RendererSynchronizationTask extends SceneRendererTask {
    private final SceneNode root;

    public RendererSynchronizationTask(SceneRenderer sceneRenderer, SceneNode root) {
        super(sceneRenderer);
        this.root = root;
    }

    @Override
    public void invoke() {
        sceneRenderer.sync(root);
    }
}
