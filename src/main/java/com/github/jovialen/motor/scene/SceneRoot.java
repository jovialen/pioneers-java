package com.github.jovialen.motor.scene;

import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;
import org.tinylog.Logger;

public class SceneRoot extends SceneNode {
    private Application application;

    public Application getApplication() {
        return application;
    }

    public EventBus getEventBus() {
        return application.getEventBus();
    }

    public Window getWindow() {
        return application.getWindow();
    }
    
    public void switchScene(Scene scene) {
        if (!isRoot()) {
            Logger.tag("SCENE").warn("Switching scene from local scene root, not root.");
        }

        application.setScene(scene);
    }
}
