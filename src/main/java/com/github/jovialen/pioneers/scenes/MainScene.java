package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;
import com.github.jovialen.motor.graph.scene.renderable.MeshNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.resource.mesh.MeshSource;

public class MainScene implements SceneSource {
    public SceneRoot instantiate(SceneRoot root) {
        root.name = "Main Scene";

        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.name = "Camera";
        cameraNode.rotation.rotateY((float) Math.toRadians(10));
        cameraNode.position.z = 2;

        Node3D meshes = root.addChild(new Node3D(root));
        meshes.name = "Meshes";
        meshes.position.z = -2;

        MeshNode quad = meshes.addChild(new MeshNode(meshes));
        quad.name = "Quad";
        quad.position.x = -0.5f;

        MeshNode triangle = meshes.addChild(new MeshNode(meshes));
        triangle.name = "Triangle";
        triangle.mesh = MeshSource.TRIANGLE;
        triangle.position.x = 0.5f;

        return root;
    }
}
