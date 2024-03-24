package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.graph.scene.SceneNode;

public class SceneNodeStartedEvent extends SceneNodeEvent {
    public SceneNodeStartedEvent(SceneNode node) {
        super(node);
    }
}
