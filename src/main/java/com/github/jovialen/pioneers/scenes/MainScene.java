package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.Scene;
import com.github.jovialen.motor.graph.scene.SceneRoot;

public class MainScene implements Scene {
    public SceneRoot instantiate(SceneRoot root) {
        root.setName("Main Scene");
        return root;
    }
}
