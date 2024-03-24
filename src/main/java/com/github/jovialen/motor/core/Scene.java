package com.github.jovialen.motor.core;

import com.github.jovialen.motor.graph.scene.SceneRoot;

public interface Scene {
    SceneRoot instantiate(SceneRoot root);
}
