package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;

public class MainScene extends Scene {
    @Override
    public SceneNode instantiate(SceneNode root) {
        Camera3DNode camera = root.addChild(new Camera3DNode());

        return root;
    }
}
