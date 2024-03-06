package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;

public class ActivateContextTask extends SceneRendererTask {
    public ActivateContextTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
        sceneRenderer.getContext().deactivate();
    }

    @Override
    public void invoke() {
        sceneRenderer.getContext().activate();
    }
}
