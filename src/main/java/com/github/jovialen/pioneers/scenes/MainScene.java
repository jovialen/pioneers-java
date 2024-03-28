package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;
import com.github.jovialen.motor.graph.scene.renderable.MeshNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.resource.mesh.MeshSource;
import org.joml.Vector3f;

public class MainScene implements SceneSource {
    public static class Velocity3D extends Node3D {
        public Vector3f velocity = new Vector3f(0);

        public Velocity3D(SceneNode parent) {
            super(parent);
        }

        @Override
        public void process(double deltaTime) {
            position.add(new Vector3f(velocity).mul((float) deltaTime));
            super.process(deltaTime);
        }
    }

    public SceneRoot instantiate(SceneRoot root) {
        root.name = "Main Scene";

        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.name = "Camera";
        cameraNode.rotation.rotateY((float) Math.toRadians(10));
        cameraNode.position.z = 2;

        Velocity3D meshes = root.addChild(new Velocity3D(root));
        meshes.velocity.z = -1;
        meshes.name = "Meshes";

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
