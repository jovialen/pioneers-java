package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.Vertex;
import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneNode;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import com.github.jovialen.motor.scene.nodes.MeshNode;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class MainScene extends Scene {
    @Override
    public SceneRoot instantiate(SceneRoot root) {
        Camera3DNode camera = root.addChild(new Camera3DNode());
        camera.clearColor = new Vector4f(1, 0, 0, 1);

        MeshNode meshNode = root.addChild(new MeshNode());
        Mesh mesh = new Mesh("Plane Mesh");
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)));
        mesh.vertices.add(new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f)));
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)));
        mesh.vertices.add(new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)));
        mesh.indices = List.of(0, 3, 1, 1, 2, 0);
        meshNode.mesh = mesh;

        return root;
    }
}
