package com.github.jovialen.motor.render.resource.mesh;

import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.DestructibleResource;
import com.github.jovialen.motor.render.resource.buffer.IndexBuffer;
import com.github.jovialen.motor.render.resource.buffer.VertexBuffer;
import com.github.jovialen.motor.utils.DataUtils;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.tinylog.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MeshBuffer implements DestructibleResource {
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;

    public MeshBuffer() {
        Logger.tag("RENDER").info("Creating mesh buffer {}", this);
        vertexBuffer = new VertexBuffer();
        indexBuffer = new IndexBuffer();
    }

    @Override
    public void destroy() {
        Logger.tag("RENDER").info("Destroying mesh buffer {}", this);
        vertexBuffer.destroy();
        indexBuffer.destroy();
    }

    public void store(MeshData meshData) {
        Logger.tag("RENDER").info("Storing {} in {}", meshData, this);

        try (GLState glState = GLState.pushSharedState()) {
            vertexBuffer.store(getVertices(meshData));
            indexBuffer.store(getIndices(meshData));
        }
    }

    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public IndexBuffer getIndexBuffer() {
        return indexBuffer;
    }

    private FloatBuffer getVertices(MeshData meshData) {
        int vertexSize = Vertex.LAYOUT.getBuffers().getFirst().stride;
        int count = vertexSize * meshData.vertices.size();
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(count);
        for (Vertex vertex : meshData.vertices) {
            vertex.store(floatBuffer);
        }
        floatBuffer.flip();
        return floatBuffer;
    }

    private IntBuffer getIndices(MeshData meshData) {
        int count = meshData.faces.size() * 3;
        IntBuffer intBuffer = BufferUtils.createIntBuffer(count);
        for (Vector3i face : meshData.faces) {
            DataUtils.store(intBuffer, face);
        }
        intBuffer.flip();
        return intBuffer;
    }
}
