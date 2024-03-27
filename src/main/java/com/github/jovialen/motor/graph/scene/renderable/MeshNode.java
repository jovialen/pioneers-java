package com.github.jovialen.motor.graph.scene.renderable;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.node.MigratedMeshNode;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.transform.Node3D;
import com.github.jovialen.motor.render.resource.material.Material;
import com.github.jovialen.motor.render.resource.mesh.MeshSource;

public class MeshNode extends Node3D implements MigrationNode<MigratedMeshNode> {
    public MeshSource mesh = MeshSource.QUAD;
    public Material material = Material.DEFAULT;

    public MeshNode(SceneNode parent) {
        super(parent);
    }

    @Override
    public MigratedMeshNode migrate(RenderNode parent) {
        return new MigratedMeshNode(parent, mesh.getMesh());
    }

    @Override
    public void synchronize(MigratedMeshNode renderNode) {
        renderNode.transform.set(transform);
        renderNode.material = material;
    }
}
