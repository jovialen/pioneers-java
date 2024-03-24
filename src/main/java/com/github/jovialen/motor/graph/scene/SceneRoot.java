package com.github.jovialen.motor.graph.scene;

import com.github.jovialen.motor.core.Application;
import com.github.jovialen.motor.window.Window;
import com.google.common.eventbus.EventBus;

public class SceneRoot extends SceneNode {
    public final Application application;

    public SceneRoot(Application application) {
        super(null);
        this.application = application;

        root = this;
        localRoot = this;
    }

    public SceneRoot(SceneNode parent, Application application) {
        super(parent);
        this.application = application;

        localRoot = this;
    }

    public Application getApplication() {
        return application;
    }

    public Window getWindow() {
        return application.getWindow();
    }

    public EventBus getEventBus() {
        return application.getEventBus();
    }

    public double getDeltaTime() {
        return application.getClock().getDeltaTime();
    }
}
