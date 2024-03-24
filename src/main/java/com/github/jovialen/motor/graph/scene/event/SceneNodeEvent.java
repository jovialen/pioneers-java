package com.github.jovialen.motor.graph.scene.event;

import com.github.jovialen.motor.graph.scene.SceneNode;

public class SceneNodeEvent extends SceneGraphEvent {
    public final SceneNode node;

    public SceneNodeEvent(SceneNode node) {
        super(node.getRoot().getScene());
        this.node = node;
    }
}
