package com.github.jovialen.motor.scene;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void start() {
        children.forEach(SceneNode::start);
    }

    public void sync() {
        children.forEach(SceneNode::sync);
    }

    public void preProcess() {
        children.forEach(SceneNode::preProcess);
    }

    public void process() {
        children.forEach(SceneNode::process);
    }

    public void postProcess() {
        children.forEach(SceneNode::postProcess);
    }

    public void stop() {
        children.forEach(SceneNode::stop);
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

    public <T extends SceneNode> List<T> getParentsOfClass(Class<T> tClass) {
        List<T> parents = new ArrayList<>();
        SceneNode current = this;
        while (current != null) {
            if (tClass.isAssignableFrom(current.getClass())) {
                parents.add((T) current);
            }
            current = current.getParent();
        }
        return parents;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public <T extends SceneNode> List<T> getChildrenOfClass(Class<T> tClass) {
        return getChildrenOfClass(tClass, Integer.MAX_VALUE);
    }

    public <T extends SceneNode> List<T> getChildrenOfClass(Class<T> tClass, int maxDepth) {
        List<T> children = new ArrayList<>();
        addChildrenOfClass(tClass, children, maxDepth);
        return children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
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

    public boolean hasSiblings() {
        return !getSiblings().isEmpty();
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

    private <T> void addChildrenOfClass(Class<T> tClass, List<T> children, int maxDepth) {
        if (maxDepth == 0) {
            return;
        }

        for (SceneNode child : this.children) {
            if (tClass.isAssignableFrom(child.getClass())) {
                T tChild = (T) child;
                children.add(tChild);
            }

            if (maxDepth > 1) {
                child.addChildrenOfClass(tClass, children, maxDepth - 1);
            }
        }
    }
}
