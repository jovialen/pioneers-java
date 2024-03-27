package com.github.jovialen.motor.render.resource.mesh;

public interface MeshSource {
    MeshSource TRIANGLE = triangle();
    MeshSource QUAD = quad();

    MeshData getMesh();

    private static MeshSource triangle() {
        return new MeshBuilder()
                .setPosition(0, 0.5f).setTextureCoordinate(0.5f, 1).addVertex()
                .setPosition(-0.5f, -0.5f).setTextureCoordinate(0, 0).addVertex()
                .setPosition(0.5f, -0.5f).setTextureCoordinate(1, 0).addVertex()
                .addFace(0, 1, 2);
    }

    private static MeshSource quad() {
        return new MeshBuilder()
                .setPosition(-0.5f, 0.5f).setTextureCoordinate(0, 1).addVertex()
                .setPosition(-0.5f, -0.5f).setTextureCoordinate(0, 0).addVertex()
                .setPosition(0.5f, -0.5f).setTextureCoordinate(1, 0).addVertex()
                .setPosition(0.5f, 0.5f).setTextureCoordinate(1, 1).addVertex()
                .addFace(0, 1, 3)
                .addFace(1, 2, 3);
    }
}
