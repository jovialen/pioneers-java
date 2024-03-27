package com.github.jovialen.motor.graph.render.node;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.buffer.IndexBuffer;
import com.github.jovialen.motor.render.resource.mesh.MeshBuffer;
import com.github.jovialen.motor.render.resource.mesh.MeshData;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.tinylog.Logger;

public class MigratedMeshNode extends RenderNode {
    public final MeshData meshData;
    public Matrix4f transform = new Matrix4f().identity();

    public MigratedMeshNode(RenderNode parent, MeshData meshData) {
        super(parent);
        this.meshData = meshData;
    }

    @Override
    public void run() {
        MeshBuffer meshBuffer = getRoot().getRenderer().getMeshes().get(meshData);
        IndexBuffer indexBuffer = meshBuffer.getBufferArray().getIndexBuffer();

        GLState.getCurrent().bindBufferArray(meshBuffer.getBufferArray().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES,
                indexBuffer.getSize(),
                indexBuffer.getDataType().glDataType(),
                0);

        super.run();
    }
}
