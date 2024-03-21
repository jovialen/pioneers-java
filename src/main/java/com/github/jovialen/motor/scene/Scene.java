package com.github.jovialen.motor.scene;

import org.tinylog.Logger;

/**
 * A template for a scene graph.
 */
public abstract class Scene {
    /**
     * Create and return the scene graph.
     * @param root Root node for the scene to instantiate in.
     * @return Should always be the same as the root parameter.
     */
    public abstract SceneRoot instantiate(SceneRoot root);

    /**
     * Create and return the scene graph.
     * @return Root node of the scene graph.
     */
    public SceneRoot instantiate() {
        SceneRoot root = instantiate(new SceneRoot());
        if (root.hasParent()) {
            Logger.tag("SCENE").warn("Root node of main scene has parent. Was the correct node returned?");
        }
        return root;
    }
}
