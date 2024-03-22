package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.renderer.SceneRenderer;
import com.github.jovialen.motor.threads.ThreadTask;

/**
 * Generic thread task relating to the scene renderer.
 */
public abstract class SceneRendererTask implements ThreadTask {
    protected final SceneRenderer sceneRenderer;

    public SceneRendererTask(SceneRenderer sceneRenderer) {
        this.sceneRenderer = sceneRenderer;
    }
}
