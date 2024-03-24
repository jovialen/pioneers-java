package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.graph.scene.SceneNode;

public class SceneNodeRemovedEvent extends SceneNodeEvent {
    public SceneNodeRemovedEvent(SceneNode node) {
        super(node);
    }
}
