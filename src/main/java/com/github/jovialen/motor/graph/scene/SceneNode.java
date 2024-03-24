package com.github.jovialen.motor.graph.scene;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.Node;
import com.github.jovialen.motor.graph.scene.event.SceneNodeAddedEvent;
import com.github.jovialen.motor.graph.scene.event.SceneNodeRemovedEvent;
import com.github.jovialen.motor.graph.scene.event.SceneNodeStartedEvent;

public class SceneNode extends Node<SceneNode> {
    SceneRoot root;
    SceneRoot localRoot;

    public String name = toString();
    private boolean started = false;

    @SuppressWarnings("CopyConstructorMissesField")
    public SceneNode(SceneNode parent) {
        super(parent);
        if (parent == null) return;
        this.root = parent.getRoot();
        this.localRoot = parent.getLocalRoot();
    }

    public SceneRoot addChild(SceneSource scene) {
        return addChild(scene.instantiate(new SceneRoot(this)));
    }

    @Override
    public <U extends SceneNode> U addChild(U child) {
        U u = super.addChild(child);
        if (started) {
            child.start();
        }
        getRoot().getEventBus().post(new SceneNodeAddedEvent(u));
        return u;
    }

    @Override
    public <U extends SceneNode> void removeChild(U child) {
        if (started) {
            child.stop();
        }
        super.removeChild(child);
        getRoot().getEventBus().post(new SceneNodeRemovedEvent(child));
    }

    public void start() {
        started = true;
        getChildren().forEach(SceneNode::start);
        getRoot().getEventBus().post(new SceneNodeStartedEvent(this));
    }

    public void process(double deltaTime) {
        getChildren().forEach((child) -> child.process(deltaTime));
    }

    public void stop() {
        getChildren().forEach(SceneNode::stop);
        started = false;

        // In case this node also is queued to be removed, abort that so that
        // it isn't removed twice
        getRoot().getScene().getToRemove().remove(this);
        getRoot().getEventBus().post(new SceneNodeRemovedEvent(this));
    }

    public void queueRemove() {
        getRoot().getScene().removeNode(this);
    }

    public SceneRoot getRoot() {
        return root;
    }

    public SceneRoot getLocalRoot() {
        return localRoot;
    }
}
