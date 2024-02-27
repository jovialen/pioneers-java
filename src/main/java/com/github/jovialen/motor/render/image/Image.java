package com.github.jovialen.motor.render.image;

import com.github.jovialen.motor.render.gl.Texture2D;

public abstract class Image {
    private final String debugName;
    private int buildId = 0;

    public Image() {
        this("");
    }

    public Image(String debugName) {
        this.debugName = debugName;
    }

    public Texture2D build() {
        Texture2D texture2D = new Texture2D(getBuildName());
        load(texture2D);
        return texture2D;
    }

    protected abstract void load(Texture2D texture2D);

    private String getBuildName() {
        buildId++;
        return (debugName + " Texture " + buildId).trim();
    }
}
