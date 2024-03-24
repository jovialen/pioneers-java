package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.graph.Node;

public class RenderNode extends Node<RenderNode> {
    public RenderNode(RenderNode parent) {
        super(parent);
    }

    public void create() {
        getChildren().forEach(RenderNode::create);
    }

    public void run() {
        getChildren().forEach(RenderNode::run);
    }

    public void destroy() {
        getChildren().forEach(RenderNode::destroy);
    }
}
