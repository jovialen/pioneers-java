package com.github.jovialen.motor.render.mesh;

import com.github.jovialen.motor.render.gl.Buffer;
import com.github.jovialen.motor.render.gl.BufferArray;
import org.lwjgl.BufferUtils;
import org.tinylog.Logger;

import java.nio.FloatBuffer;
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

    public BufferArray build() {
        String buildName = generateBufferArrayName();
        BufferArray bufferArray = new BufferArray(buildName);

        if (!indices.isEmpty()) {
            Buffer.IndexBuffer indexBuffer = buildIndexBuffer(buildName);
            bufferArray.setIndexBuffer(indexBuffer, true);
        }

        Buffer.VertexBuffer vertexBuffer = buildVertexBuffer(buildName);
        List<Buffer.Slice> slices = vertexBuffer.getSlices(Vertex.LAYOUT);
        for (int i = 0; i < Vertex.LAYOUT.length; i++) {
            bufferArray.insert(i, slices.get(i), Vertex.LAYOUT[i], true);
        }

        return bufferArray;
    }

    private Buffer.IndexBuffer buildIndexBuffer(String buildName) {
        String indexBufferName = buildName.isEmpty() ? "" : buildName + " Index Buffer";
        Buffer.IndexBuffer indexBuffer = new Buffer.IndexBuffer(indexBufferName);

        int[] list = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            list[i] = indices.get(i);
        }
        indexBuffer.store(list);

        return indexBuffer;
    }

    private Buffer.VertexBuffer buildVertexBuffer(String buildName) {
        String vertexBufferName = buildName.isEmpty() ? "" : buildName + " Vertex Buffer";
        Buffer.VertexBuffer vertexBuffer = new Buffer.VertexBuffer(vertexBufferName);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(Vertex.getVertexSize() * vertices.size());
        for (Vertex vertex : vertices) {
            buffer = vertex.put(buffer);
        }
        buffer.flip();

        vertexBuffer.store(buffer);
        return vertexBuffer;
    }

    private String generateBufferArrayName() {
        if (debugName != null && !debugName.isEmpty()) {
            buildIndex++;
            return (debugName + " Mesh Buffer Array " + buildIndex).trim();
        }
        return "";
    }
}
