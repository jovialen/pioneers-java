package com.github.jovialen.motor.render.resource.mesh;

import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class MeshData {
    public final List<Vertex> vertices = new ArrayList<>();
    public final List<Vector3i> faces = new ArrayList<>();

    public MeshData() {
    }

    public MeshData(MeshData other) {
        this.vertices.addAll(other.vertices);
        this.faces.addAll(other.faces);
    }
}
