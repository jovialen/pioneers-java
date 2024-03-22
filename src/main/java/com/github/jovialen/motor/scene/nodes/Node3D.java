package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.scene.SceneNode;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

/**
 * A node in 3-dimensional space.
 */
public class Node3D extends SceneNode {
    /**
     * Position relative to parent node.
     */
    public Vector3f position = new Vector3f(0);
    /**
     * Rotation relative to parent node.
     */
    public Quaternionf rotation = new Quaternionf().identity();
    /**
     * Scale relative to parent node.
     */
    public Vector3f scale = new Vector3f(1);

    public Vector3f getGlobalPosition() {
        Vector3f globalPosition = position;
        for (Node3D parent : getParentsOfClass(Node3D.class, false)) {
            globalPosition = globalPosition.add(parent.position);
        }
        return globalPosition;
    }

    public Quaternionf getGlobalRotation() {
        Quaternionf globalRotation = rotation;
        for (Node3D parent : getParentsOfClass(Node3D.class, false)) {
            globalRotation = globalRotation.add(parent.rotation);
        }
        return globalRotation;
    }

    public Vector3f getGlobalScale() {
        Vector3f globalScale = scale;
        for (Node3D parent : getParentsOfClass(Node3D.class, false)) {
            globalScale = globalScale.add(parent.scale);
        }
        return globalScale;
    }

    public Matrix4f getTransform(Matrix4f matrix4f) {
        matrix4f.translate(position);
        matrix4f.rotate((Quaternionfc) rotation);
        matrix4f.scale(scale);
        return matrix4f;
    }

    public Matrix4f getGlobalTransform(Matrix4f matrix4f) {
        matrix4f = getTransform(matrix4f);
        for (Node3D parent : getParentsOfClass(Node3D.class, false)) {
            matrix4f = parent.getTransform(matrix4f);
        }
        return matrix4f;
    }
}
