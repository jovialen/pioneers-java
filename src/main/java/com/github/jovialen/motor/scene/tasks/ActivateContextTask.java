package com.github.jovialen.motor.scene.tasks;

import com.github.jovialen.motor.scene.SceneRenderer;
import org.tinylog.Logger;

/**
 * Activate the render context.
 */
public class ActivateContextTask extends SceneRendererTask {
    public ActivateContextTask(SceneRenderer sceneRenderer) {
        super(sceneRenderer);
        // Assume the context is active on the current thread, and deactivate
        // it
        sceneRenderer.getContext().deactivate();
    }

    @Override
    public void invoke() {
        Logger.tag("RENDER").debug("Activating render context");
        sceneRenderer.getContext().activate();
    }
}
