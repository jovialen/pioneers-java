package com.github.jovialen.motor.render.mesh;

import com.github.jovialen.motor.render.gl.Buffer;
import com.github.jovialen.motor.render.gl.BufferArray;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

public class MeshBuffer extends BufferArray {
    private final Buffer.VertexBuffer vertexBuffer;
    private final Buffer.IndexBuffer indexBuffer;

    public MeshBuffer() {
        this("");
    }

    public MeshBuffer(String debugName) {
        super(debugName);
        vertexBuffer = new Buffer.VertexBuffer(debugName + " Vertex Buffer");
        indexBuffer = new Buffer.IndexBuffer(debugName + " Index Buffer");

        setIndexBuffer(indexBuffer);
        List<Buffer.Slice> slices = vertexBuffer.getSlices(Vertex.LAYOUT);
        for (int i = 0; i < Vertex.LAYOUT.length; i++) {
            insert(i, slices.get(i), Vertex.LAYOUT[i]);
        }
    }

    @Override
    public void destroy() {
        indexBuffer.destroy();
        vertexBuffer.destroy();
        super.destroy();
    }

    public void update(Mesh mesh) {
        if (!mesh.indices.isEmpty()) {
            int[] list = new int[mesh.indices.size()];
            for (int i = 0; i < mesh.indices.size(); i++) {
                list[i] = mesh.indices.get(i);
            }
            indexBuffer.store(list);
        }

        FloatBuffer buffer = BufferUtils.createFloatBuffer(Vertex.getVertexSize() * mesh.vertices.size());
        for (Vertex vertex : mesh.vertices) {
            buffer = vertex.put(buffer);
        }
        buffer.flip();

        vertexBuffer.store(buffer);
    }

    public Buffer.VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public Buffer.IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }
}
