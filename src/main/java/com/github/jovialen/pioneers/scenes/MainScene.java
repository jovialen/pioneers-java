package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;
import com.github.jovialen.motor.graph.scene.renderable.MeshNode;
import com.github.jovialen.motor.render.resource.material.DefaultMaterial;
import com.github.jovialen.motor.render.resource.mesh.MeshSource;
import com.github.jovialen.motor.render.resource.shader.ShaderBuilder;
import com.github.jovialen.motor.render.resource.shader.ShaderSource;
import org.joml.Vector4f;

import java.nio.file.Path;

public class MainScene implements SceneSource {
    public SceneRoot instantiate(SceneRoot root) {
        root.name = "Main Scene";

        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.name = "Camera";
        cameraNode.camera.clearColor.x = 0;
        cameraNode.camera.clearColor.y = 0;

        MeshNode meshNode = root.addChild(new MeshNode(root));
        meshNode.mesh = MeshSource.QUAD;

        return root;
    }
}
