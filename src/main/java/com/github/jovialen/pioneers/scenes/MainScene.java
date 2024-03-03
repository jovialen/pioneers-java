package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import org.joml.Vector4f;

public class MainScene extends Scene {
    @Override
    public SceneNode instantiate(SceneNode root) {
        Camera3DNode camera = root.addChild(new Camera3DNode());
        camera.clearColor = new Vector4f(1, 0, 0, 1);

        return root;
    }
}
