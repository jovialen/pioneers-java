package com.github.jovialen.motor.scene;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    SceneRoot root;
    SceneRoot localRoot;

    private final SceneNode parent;
    private final List<SceneNode> children = new ArrayList<>();

    private String name;

    public SceneNode() {
        root = null;
        localRoot = null;
        parent = null;
    }

    public SceneNode(@NonNull SceneNode parent) {
        this.root = parent.getRoot();
        this.localRoot = parent.getLocalRoot();
        this.parent = parent;
    }

    public void start() {
        children.forEach(SceneNode::start);
    }

    public void process(double deltaTime) {
        children.forEach((child) -> child.process(deltaTime));
    }

    public void stop() {
        children.forEach(SceneNode::stop);
    }

    public SceneRoot getRoot() {
        return root;
    }

    public SceneRoot getLocalRoot() {
        return localRoot;
    }

    public SceneNode getParent() {
        return parent;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
