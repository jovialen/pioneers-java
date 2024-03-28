package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.graph.Node;
import com.github.jovialen.motor.render.context.GLState;
import org.tinylog.Logger;

public class RenderNode extends Node<RenderNode> {
    RenderRoot root;
    private boolean created = false;

    @SuppressWarnings("CopyConstructorMissesField")
    public RenderNode(RenderNode parent) {
        super(parent);
        if (parent == null) return;
        root = parent.root;
    }

    public void create() {
        created = true;
        getChildren().forEach(RenderNode::create);
    }

    public void run(GLState glState) {
        getChildren().forEach((node) -> node.run(glState));
    }

    public void destroy() {
        getChildren().forEach(RenderNode::destroy);
        created = false;
    }

    @Override
    public <U extends RenderNode> U addChild(U child) {
        if (created) {
            Logger.tag("RENDER").error("Attempting to add node to existing render graph. Recreate render graph instead");
            return null;
        }

        return super.addChild(child);
    }

    @Override
    public <U extends RenderNode> void removeChild(U child) {
        Logger.tag("RENDER").error("Attempted to remove node from render graph. Recreate render graph instead");
    }

    public RenderRoot getRoot() {
        return root;
    }
}
