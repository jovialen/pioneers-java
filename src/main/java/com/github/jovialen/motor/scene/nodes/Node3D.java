package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.scene.SceneNode;
import org.joml.Quaterniond;
import org.joml.Vector3f;

public class Node3D extends SceneNode {
    public Vector3f position = new Vector3f(0);
    public Quaterniond rotation = new Quaterniond().identity();
    public Vector3f scale = new Vector3f(1);

    public Vector3f getGlobalPosition() {
        Vector3f globalPosition = position;
        for (Node3D parent : getParentsOfClass(Node3D.class)) {
            globalPosition = globalPosition.add(parent.position);
        }
        return globalPosition;
    }

    public Quaterniond getGlobalRotation() {
        Quaterniond globalRotation = rotation;
        for (Node3D parent : getParentsOfClass(Node3D.class)) {
            globalRotation = globalRotation.add(parent.rotation);
        }
        return globalRotation;
    }

    public Vector3f getGlobalScale() {
        Vector3f globalScale = scale;
        for (Node3D parent : getParentsOfClass(Node3D.class)) {
            globalScale = globalScale.add(parent.scale);
        }
        return globalScale;
    }
}
