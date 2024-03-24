package com.github.jovialen.motor.graph.scene.renderable;

import com.github.jovialen.motor.graph.render.RenderNode;

public interface MigrationNode<T extends RenderNode> {
    T migrate(RenderNode parent);
    void synchronize(T renderNode);

    default void dumbSynchronize(RenderNode renderNode) {
        // This is very cursed, but as long as this method is never called with
        // anything else than the render node it created during migration,
        // it should be fine.

        // After all, there is *no way* this method could ever be called with
        // anything else, right?

        //noinspection unchecked
        synchronize((T) renderNode);
    }
}
