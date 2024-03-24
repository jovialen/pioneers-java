package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;

public class MainScene implements SceneSource {
    public SceneRoot instantiate(SceneRoot root) {
        root.setName("Main Scene");

        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.setName("Camera");
        cameraNode.clearColor.y = 0;

        return root;
    }
}
