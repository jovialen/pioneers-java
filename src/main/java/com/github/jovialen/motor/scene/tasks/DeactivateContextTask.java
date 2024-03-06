package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;

public class DeactivateContextTask extends SceneRendererTask {
    public DeactivateContextTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
    }

    @Override
    public void invoke() {
        sceneRenderer.getContext().deactivate();
    }
}
