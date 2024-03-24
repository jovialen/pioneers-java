package com.github.jovialen.motor.graph;

import com.github.jovialen.motor.graph.scene.SceneNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<T extends Node<T>> {
    private final T parent;
    private final List<T> children = new ArrayList<>();

    public Node(T parent) {
        this.parent = parent;
    }

    public T getParent() {
        return parent;
    }

    public List<T> getChildren() {
        return children;
    }
}
