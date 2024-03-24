package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.core.Scene;

public class SceneGraphEvent {
    public final Scene scene;

    public SceneGraphEvent(Scene scene) {
        this.scene = scene;
    }
}
