package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;
import org.tinylog.Logger;

/**
 * Deactivate the render context.
 */
public class DeactivateContextTask extends SceneRendererTask {
    public DeactivateContextTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
    }

    @Override
    public void invoke() {
        Logger.tag("RENDER").debug("Deactivating render context");
        sceneRenderer.getContext().deactivate();
    }
}
