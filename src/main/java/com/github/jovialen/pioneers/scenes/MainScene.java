package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneRoot;

public class MainScene implements SceneSource {
    public SceneRoot instantiate(SceneRoot root) {
        root.setName("Main Scene");
        return root;
    }
}
