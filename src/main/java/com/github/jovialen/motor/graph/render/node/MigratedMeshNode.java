package com.github.jovialen.motor.graph.render.node;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.buffer.BufferArray;
import com.github.jovialen.motor.render.resource.buffer.IndexBuffer;
import com.github.jovialen.motor.render.resource.material.Material;
import com.github.jovialen.motor.render.resource.mesh.MeshBuffer;
import com.github.jovialen.motor.render.resource.mesh.MeshData;
import com.github.jovialen.motor.render.resource.shader.ShaderProgram;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.tinylog.Logger;

public class MigratedMeshNode extends RenderNode {
    public final MeshData meshData;
    public BufferArray bufferArray;
    public Material material;
    public Matrix4f transform = new Matrix4f().identity();

    public MigratedMeshNode(RenderNode parent, MeshData meshData) {
        super(parent);
        this.meshData = meshData;
        this.bufferArray = new BufferArray();
    }

    @Override
    public void create() {
        MeshBuffer meshBuffer = getRoot().getRenderer().getMeshes().get(meshData);
        bufferArray.setIndexBuffer(meshBuffer.getIndexBuffer());
        bufferArray.setVertexBuffer(0, meshBuffer.getVertexBuffer());

        super.create();
    }

    @Override
    public void run(GLState glState) {
        ShaderProgram program = getRoot().getRenderer().getShaders().get(material.shader);
        MeshBuffer meshBuffer = getRoot().getRenderer().getMeshes().get(meshData);
        IndexBuffer indexBuffer = meshBuffer.getIndexBuffer();

        glState.bindBufferArray(bufferArray.getId());
        glState.bindShaderProgram(program.getId());
        bufferArray.format(program);

        GL11.glDrawElements(GL11.GL_TRIANGLES,
                indexBuffer.getSize(),
                indexBuffer.getDataType().glDataType(),
                0);

        super.run(glState);
    }
}
