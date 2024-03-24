package com.github.jovialen.motor.core;

import com.github.jovialen.motor.graph.scene.SceneRoot;

public interface SceneSource {
    SceneRoot instantiate(SceneRoot root);
}
