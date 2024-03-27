package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.context.GLContext;
import com.github.jovialen.motor.render.resource.ResourceStorage;
import com.github.jovialen.motor.render.resource.mesh.MeshBuffer;
import com.github.jovialen.motor.render.resource.mesh.MeshData;
import com.github.jovialen.motor.render.resource.mesh.MeshProvider;
import com.github.jovialen.motor.render.resource.shader.ShaderProgram;
import com.github.jovialen.motor.render.resource.shader.ShaderProvider;
import com.github.jovialen.motor.render.resource.shader.ShaderSource;
import org.tinylog.Logger;

public class Renderer {
    private final GLContext context;
    private final ResourceStorage<MeshData, MeshBuffer> meshes = new ResourceStorage<>(new MeshProvider());
    private final ResourceStorage<ShaderSource, ShaderProgram> shaders = new ResourceStorage<>(new ShaderProvider());

    public Renderer(GLContext context) {
        Logger.tag("RENDER").info("Creating renderer");
        this.context = context;
    }

    public void destroy() {
        Logger.tag("RENDER").info("Destroying renderer");
        meshes.destroy();
        shaders.destroy();
    }

    public GLContext getContext() {
        return context;
    }

    public ResourceStorage<MeshData, MeshBuffer> getMeshes() {
        return meshes;
    }

    public ResourceStorage<ShaderSource, ShaderProgram> getShaders() {
        return shaders;
    }
}
