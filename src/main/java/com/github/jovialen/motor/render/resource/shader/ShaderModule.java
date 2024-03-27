package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.DestructibleResource;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

public class ShaderModule implements DestructibleResource {
    private final int id;

    public ShaderModule(ShaderStage stage) {
        this(GL20.glCreateShader(stage.glStage()));
    }

    public ShaderModule(int id) {
        Logger.tag("GL").debug("Creating shader module {}", id);
        this.id = id;
    }

    @Override
    public void destroy() {
        Logger.tag("GL").debug("Destroying shader module {}", id);
        GL20.glDeleteShader(id);
    }

    public void setSource(String source) {
        GL20.glShaderSource(id, source);
    }

    public boolean compile() {
        GL20.glCompileShader(id);
        boolean compiled = GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL20.GL_TRUE;
        if (!compiled) {
            String error = GL20.glGetShaderInfoLog(id);
            Logger.tag("GL").error("Failed to compile shader module: {}", error);
        }
        return compiled;
    }

    public int getId() {
        return id;
    }
}
