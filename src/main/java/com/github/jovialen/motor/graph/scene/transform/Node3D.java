package com.github.jovialen.motor.graph.scene.transform;

import com.github.jovialen.motor.graph.scene.SceneNode;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Node3D extends SceneNode {
    public Vector3f position = new Vector3f(0);
    public Quaternionf rotation = new Quaternionf().identity();
    public Vector3f scale = new Vector3f(1);
    public boolean relative = true;

    public Matrix4f transform = new Matrix4f().identity();

    public Node3D(SceneNode parent) {
        super(parent);
    }

    @Override
    public void process(double deltaTime) {
        Node3D parent = getParent(Node3D.class);
        if (parent != null && relative) {
            transform.set(parent.transform);
        } else {
            transform.identity();
        }
        transform.translate(position).rotate(rotation).scale(scale);

        super.process(deltaTime);
    }
}
