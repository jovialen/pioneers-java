package com.github.jovialen.motor.scene;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    SceneNode root;
    SceneNode localRoot;
    SceneNode parent;
    final List<SceneNode> children = new ArrayList<>();

    public SceneNode() {
        root = this;
        localRoot = this;
        parent = null;
    }

    public SceneNode getRoot() {
        return root;
    }

    public boolean isRoot() {
        return root == this || !hasParent();
    }

    public SceneNode getLocalRoot() {
        return localRoot;
    }

    public boolean isLocalRoot() {
        return localRoot == this;
    }

    public SceneNode getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public List<SceneNode> getSiblings() {
        if (parent == null) {
            Logger.tag("SCENE").warn("Attempting to get siblings of root node");
            return null;
        }
        return parent.getChildren().stream()
                .filter((SceneNode sibling) -> sibling != this)
                .toList();
    }

    public SceneNode addChild(Scene localScene) {
        Logger.tag("SCENE").debug("Instantiating local scene {} as child of {}", localScene, this);
        SceneNode localRoot = localScene.instantiate();
        localRoot.root = root;
        localRoot.parent = this;
        children.add(localRoot);
        return localRoot;
    }

    public <T extends SceneNode> T addChild(T node) {
        Logger.tag("SCENE").debug("Adding child {} to {}", node, this);
        node.root = root;
        node.localRoot = localRoot;
        node.parent = this;
        children.add(node);
        return node;
    }
}
