package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.context.GLContext;
import com.github.jovialen.motor.render.resource.ResourceStorage;
import com.github.jovialen.motor.render.resource.mesh.MeshBuffer;
import com.github.jovialen.motor.render.resource.mesh.MeshData;
import com.github.jovialen.motor.render.resource.mesh.MeshProvider;
import org.tinylog.Logger;

public class Renderer {
    private final GLContext context;
    private final ResourceStorage<MeshData, MeshBuffer> meshes = new ResourceStorage<>(new MeshProvider());

    public Renderer(GLContext context) {
        Logger.tag("RENDER").info("Creating renderer");
        this.context = context;
    }

    public void destroy() {
        Logger.tag("RENDER").info("Destroying renderer");
        meshes.destroy();
    }

    public GLContext getContext() {
        return context;
    }

    public ResourceStorage<MeshData, MeshBuffer> getMeshes() {
        return meshes;
    }
}
