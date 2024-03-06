package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;
import com.github.jovialen.motor.threads.ThreadTask;

public abstract class SceneRendererTask implements ThreadTask {
    protected final SceneRenderer sceneRenderer;

    public SceneRendererTask(SceneRenderer sceneRenderer) {
        this.sceneRenderer = sceneRenderer;
    }
}
