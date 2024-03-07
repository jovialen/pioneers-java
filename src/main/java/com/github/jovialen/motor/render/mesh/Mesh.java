package com.github.jovialen.motor.render.mesh;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    public final String debugName;
    public List<Vertex> vertices = new ArrayList<>();
    public List<Integer> indices = new ArrayList<>();

    private int buildIndex = 0;

    public Mesh() {
        this("");
    }

    public Mesh(String debugName) {
        this.debugName = debugName;
    }

    public Mesh(List<Vertex> vertices) {
        this("", vertices);
    }

    public Mesh(String debugName, List<Vertex> vertices) {
        this.debugName = debugName;
        this.vertices = vertices;
    }

    public Mesh(List<Vertex> vertices, List<Integer> indices) {
        this("", vertices, indices);
    }

    public Mesh(String debugName, List<Vertex> vertices, List<Integer> indices) {
        Logger.tag("RENDER").info("Creating mesh {}", debugName);
        this.debugName = debugName;
        this.vertices = vertices;
        this.indices = indices;
    }

    public MeshBuffer build() {
        MeshBuffer buffer = new MeshBuffer(generateBuildName());
        buffer.update(this);
        return buffer;
    }

    private String generateBuildName() {
        if (debugName != null && !debugName.isEmpty()) {
            buildIndex++;
            return (debugName + " Mesh Buffer " + buildIndex).trim();
        }
        return "";
    }
}
