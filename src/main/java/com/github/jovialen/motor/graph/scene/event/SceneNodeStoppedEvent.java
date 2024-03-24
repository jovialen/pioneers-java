package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.graph.scene.SceneNode;

public class SceneNodeStoppedEvent extends SceneNodeEvent {
    public SceneNodeStoppedEvent(SceneNode node) {
        super(node);
    }
}
