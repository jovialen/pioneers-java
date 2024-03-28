package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;
import com.github.jovialen.motor.graph.scene.renderable.MeshNode;
import com.github.jovialen.motor.render.resource.mesh.MeshSource;

public class MainScene implements SceneSource {
    public SceneRoot instantiate(SceneRoot root) {
        root.name = "Main Scene";

        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.name = "Camera";

        MeshNode meshNode = root.addChild(new MeshNode(root));
        meshNode.position.z = -2;

        return root;
    }
}
