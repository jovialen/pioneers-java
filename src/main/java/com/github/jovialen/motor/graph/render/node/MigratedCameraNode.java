package com.github.jovialen.motor.graph.render.node;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.render.Camera;
import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.Surface;
import com.github.jovialen.motor.render.resource.buffer.UniformBuffer;
import com.github.jovialen.motor.render.resource.shader.ShaderProgram;
import com.github.jovialen.motor.utils.DataUtils;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.Objects;

public class MigratedCameraNode extends RenderNode {
    public Matrix4f transform = new Matrix4f().identity();
    public Matrix4f projection = new Matrix4f().identity();
    public FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(16 * 2);

    public Camera camera = new Camera();

    public MigratedCameraNode(RenderNode parent) {
        super(parent);
    }

    public void render(GLState glState) {
        Surface target = getSurface();
        Vector2i resolution = target.getResolution();
        Vector4f clearColor = camera.clearColor;

        updateProjection(resolution);

        glState.bindFrameBuffer(target.getId());
        GL11.glViewport(0, 0, resolution.x, resolution.y);
        GL11.glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        getRoot().run(GLState.getCurrent());
    }

    private Surface getSurface() {
        return Objects.requireNonNullElse(camera.target, getRoot().getWindow());
    }

    private void updateProjection(Vector2i resolution) {
        float aspect = (float) resolution.x / (float) resolution.y;
        projection.setPerspective(camera.fov, aspect, camera.nearPlane, camera.farPlane);

        for (ShaderProgram program : getRoot().getRenderer().getShaders().values()) {
            /*UniformBuffer buffer = program.getUniforms().getUniformBuffer("uCamera");
            FloatBuffer data = buffer.map();

            DataUtils.store(data, transform);
            DataUtils.store(data, projection);
            data.flip();

            buffer.unmap();*/
            program.getUniforms().setUniform("uView", transform);
            program.getUniforms().setUniform("uProjection", projection);
        }
    }
}
