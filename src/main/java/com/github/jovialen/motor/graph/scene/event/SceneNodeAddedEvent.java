package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.graph.scene.SceneNode;

public class SceneNodeAddedEvent extends SceneNodeEvent {
    public SceneNodeAddedEvent(SceneNode node) {
        super(node);
    }
}
