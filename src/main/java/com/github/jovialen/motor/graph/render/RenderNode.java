package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.graph.Node;

public class RenderNode extends Node<RenderNode> {
    public RenderNode(RenderNode parent) {
        super(parent);
    }

    public void run() {
        getChildren().forEach(RenderNode::run);
    }
}
