package com.github.jovialen.motor.render.resource.mesh;

import com.github.jovialen.motor.render.resource.layout.DataType;
import com.github.jovialen.motor.render.resource.layout.vertex.VertexLayout;
import com.github.jovialen.motor.render.resource.layout.vertex.VertexLayoutBuilder;
import com.github.jovialen.motor.utils.DataUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;

public class Vertex {
    public static final VertexLayout LAYOUT = new VertexLayoutBuilder()
            .addBuffer().perVertex().setOffset(0)
            .addAttribute("iPosition", DataType.FLOAT3)
            .addAttribute("iTextureCoordinate", DataType.FLOAT2)
            .addAttribute("iColor", DataType.FLOAT4)
            .build();

    public Vector3f position = new Vector3f(0);
    public Vector2f textureCoordinate = new Vector2f(0);
    public Vector4f color = new Vector4f(1);

    public void store(FloatBuffer floatBuffer) {
        DataUtils.store(floatBuffer, position);
        DataUtils.store(floatBuffer, textureCoordinate);
        DataUtils.store(floatBuffer, color);
    }
}
