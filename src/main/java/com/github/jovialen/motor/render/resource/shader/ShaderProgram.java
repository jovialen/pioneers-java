package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.DestructibleResource;
import com.github.jovialen.motor.render.resource.layout.VertexLayout;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShaderProgram implements DestructibleResource {
    private final int id;
    private final VertexLayout vertexLayout;
    private final Map<String, Integer> attributeLocations = new HashMap<>();

    public ShaderProgram(VertexLayout vertexLayout) {
        this(GL20.glCreateProgram(), vertexLayout);
    }

    public ShaderProgram(int id, VertexLayout vertexLayout) {
        Logger.tag("GL").info("Creating shader program {}", id);
        this.id = id;
        this.vertexLayout = vertexLayout;
    }

    @Override
    public void destroy() {
        Logger.tag("GL").info("Destroying shader program {}", id);
        GL20.glDeleteProgram(id);
    }

    public void addShaderModule(ShaderModule module) {
        GL20.glAttachShader(id, module.getId());
    }

    public boolean link() {
        GL20.glLinkProgram(id);

        boolean linked = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL20.GL_TRUE;
        if (!linked) {
            String error = GL20.glGetProgramInfoLog(id);
            Logger.tag("GL").error("Failed to link shader program: {}", error);
        }

        return linked;
    }

    public int getAttributeLocation(String name) {
        if (id == 0 && Objects.equals(name, "position")) return 0;
        if (id == 0) return -1;
        if (!attributeLocations.containsKey(name)) {
            int location = GL20.glGetAttribLocation(id, name);
            attributeLocations.put(name, location);
        }
        return attributeLocations.get(name);
    }

    public int getId() {
        return id;
    }

    public VertexLayout getVertexLayout() {
        return vertexLayout;
    }
}
