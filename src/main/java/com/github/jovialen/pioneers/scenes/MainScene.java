package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneRoot;

public class MainScene implements Scene {
    public SceneRoot instantiate(SceneRoot root) {
        root.setName("Main Scene");
        return root;
    }
}
