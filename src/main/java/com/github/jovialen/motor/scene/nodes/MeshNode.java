package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.MeshBuffer;

public class MeshNode extends Node3D {
    public Mesh mesh;
    public MeshBuffer meshBuffer;
    private boolean shouldUpdate = true;

    public void update() {
        shouldUpdate = true;
    }

    @Override
    public void sync() {
        super.sync();

        if (shouldUpdate && mesh != null) {
            if (meshBuffer == null) {
                meshBuffer = mesh.build();
            } else {
                meshBuffer.update(mesh);
            }
            shouldUpdate = false;
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
