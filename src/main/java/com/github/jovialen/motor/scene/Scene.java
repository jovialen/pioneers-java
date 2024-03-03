package com.github.jovialen.motor.scene;

import org.tinylog.Logger;

public abstract class Scene {
    public abstract SceneNode instantiate(SceneNode root);

    public SceneNode instantiate() {
        SceneNode root = instantiate(new SceneNode());
        if (root.hasParent()) {
            Logger.tag("SCENE").warn("Root node of main scene has parent. Was the correct node returned?");
        }
        return root;
    }
}
