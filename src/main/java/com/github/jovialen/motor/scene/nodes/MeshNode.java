package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.MeshBuffer;
import com.github.jovialen.motor.scene.SceneRenderer;

public class MeshNode extends Node3D {
    public Mesh mesh;
    public MeshBuffer meshBuffer;
    private boolean shouldUpdate = true;

    public void updateMesh() {
        shouldUpdate = true;
    }

    @Override
    public void sync(SceneRenderer renderer) {
        super.sync(renderer);

        if (!shouldUpdate || mesh == null) return;
        shouldUpdate = false;

        if (meshBuffer == null) {
            meshBuffer = mesh.build();
        } else {
            meshBuffer.update(mesh);
        }
    }

    @Override
    public void stop() {
        super.stop();

        if (meshBuffer != null) {
            meshBuffer.destroy();
        }
    }
}
