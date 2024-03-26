package com.github.jovialen.motor.graph.render.node;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.render.resource.mesh.MeshData;
import org.joml.Matrix4f;

public class MigratedMeshNode extends RenderNode {
    public final MeshData meshData;
    public Matrix4f transform = new Matrix4f().identity();

    public MigratedMeshNode(RenderNode parent, MeshData meshData) {
        super(parent);
        this.meshData = meshData;
    }
}
