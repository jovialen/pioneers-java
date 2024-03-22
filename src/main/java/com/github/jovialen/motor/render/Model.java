package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.image.Image;
import com.github.jovialen.motor.render.mesh.Mesh;

public class Model {
    public Mesh mesh;
    public Image[] images = new Image[]{};

    public Model() {
    }

    public Model(Mesh mesh) {
        this.mesh = mesh;
    }

    public Model(Mesh mesh, Image... images) {
        this.mesh = mesh;
        this.images = images;
    }
}
