package com.github.jovialen.motor.render.resource.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

public class MeshBuilder implements MeshSource {
    private final MeshData meshData = new MeshData();
    private Vertex vertex;

    public MeshBuilder addVertex() {
        addVertex(vertex);
        vertex = new Vertex();
        return this;
    }

    public MeshBuilder addVertex(Vertex vertex) {
        meshData.vertices.add(vertex);
        return this;
    }

    public MeshBuilder addFace(int a, int b, int c) {
        return addFace(new Vector3i(a, b, c));
    }

    public MeshBuilder addFace(Vector3i face) {
        meshData.faces.add(face);
        return this;
    }

    public MeshBuilder setPosition(float x, float y) {
        return setPosition(x, y, 0);
    }

    public MeshBuilder setPosition(float x, float y, float z) {
        return setPosition(new Vector3f(x, y, z));
    }

    public MeshBuilder setPosition(Vector3f position) {
        vertex.position = position;
        return this;
    }

    public MeshBuilder setTextureCoordinate(float u, float v) {
        return setTextureCoordinate(new Vector2f(u, v));
    }

    public MeshBuilder setTextureCoordinate(Vector2f textureCoordinate) {
        vertex.textureCoordinate = textureCoordinate;
        return this;
    }

    public MeshBuilder setColor(float r, float g, float b) {
        return setColor(r, g, b, 1);
    }

    public MeshBuilder setColor(float r, float g, float b, float a) {
        return setColor(new Vector4f(r, g, b, a));
    }

    public MeshBuilder setColor(Vector4f color) {
        vertex.color = color;
        return this;
    }

    @Override
    public MeshData getMesh() {
        return new MeshData(meshData);
    }
}
