package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.context.GLState;
import com.github.jovialen.motor.render.resource.layout.DataType;
import com.github.jovialen.motor.render.resource.layout.uniform.UniformLayout;
import org.joml.*;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class ShaderUniforms {
    private final ShaderProgram shaderProgram;
    private final UniformLayout uniformLayout;
    private final Map<String, Integer> locations = new HashMap<>();

    public ShaderUniforms(ShaderProgram shaderProgram, UniformLayout uniformLayout) {
        this.shaderProgram = shaderProgram;
        this.uniformLayout = uniformLayout;
    }

    public void setUniform(String name, float data) {
        if (!valid(name, DataType.FLOAT)) {
            Logger.tag("GL").warn("Setting uniform of type {} to FLOAT", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform1f(location, data);
        }
    }

    public void setUniform(String name, Vector2f data) {
        if (!valid(name, DataType.FLOAT2)) {
            Logger.tag("GL").warn("Setting uniform of type {} to FLOAT2", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform2f(location, data.x, data.y);
        }
    }

    public void setUniform(String name, Vector3f data) {
        if (!valid(name, DataType.FLOAT3)) {
            Logger.tag("GL").warn("Setting uniform of type {} to FLOAT3", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform3f(location, data.x, data.y, data.z);
        }
    }

    public void setUniform(String name, Vector4f data) {
        if (!valid(name, DataType.FLOAT4)) {
            Logger.tag("GL").warn("Setting uniform of type {} to FLOAT4", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform4f(location, data.x, data.y, data.z, data.w);
        }
    }

    public void setUniform(String name, int data) {
        if (!valid(name, DataType.INT)) {
            Logger.tag("GL").warn("Setting uniform of type {} to INT", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform1i(location, data);
        }
    }

    public void setUniform(String name, Vector2i data) {
        if (!valid(name, DataType.INT2)) {
            Logger.tag("GL").warn("Setting uniform of type {} to INT2", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform2i(location, data.x, data.y);
        }
    }

    public void setUniform(String name, Vector3i data) {
        if (!valid(name, DataType.INT3)) {
            Logger.tag("GL").warn("Setting uniform of type {} to INT3", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform3i(location, data.x, data.y, data.z);
        }
    }

    public void setUniform(String name, Vector4i data) {
        if (!valid(name, DataType.INT4)) {
            Logger.tag("GL").warn("Setting uniform of type {} to INT4", uniformLayout.getAttribute(name));
        }

        try (GLState glState = GLState.pushSharedState()) {
            int location = getLocation(glState, name);

            glState.bindShaderProgram(shaderProgram.getId());
            GL20.glUniform4i(location, data.x, data.y, data.z, data.w);
        }
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public UniformLayout getUniformLayout() {
        return uniformLayout;
    }

    private boolean valid(String name, DataType as) {
        return uniformLayout.hasAttribute(name) && uniformLayout.getAttribute(name) == as;
    }

    private int getLocation(GLState glState, String name) {
        glState.bindShaderProgram(shaderProgram.getId());

        if (!locations.containsKey(name)) {
            locations.put(name, GL20.glGetUniformLocation(shaderProgram.getId(), name));
        }
        return locations.get(name);
    }
}
