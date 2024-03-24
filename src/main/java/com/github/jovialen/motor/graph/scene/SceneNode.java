package com.github.jovialen.motor.graph.scene;

import com.github.jovialen.motor.graph.Node;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SceneNode extends Node<SceneNode> {
    SceneRoot root;
    SceneRoot localRoot;

    private String name = toString();

    public SceneNode(SceneNode parent) {
        super(parent);
        if (parent == null) return;
        this.root = parent.getRoot();
        this.localRoot = parent.getLocalRoot();
    }

    public void start() {
        getChildren().forEach(SceneNode::start);
    }

    public void process(double deltaTime) {
        getChildren().forEach((child) -> child.process(deltaTime));
    }

    public void stop() {
        getChildren().forEach(SceneNode::stop);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SceneRoot getRoot() {
        return root;
    }

    public SceneRoot getLocalRoot() {
        return localRoot;
    }
}
