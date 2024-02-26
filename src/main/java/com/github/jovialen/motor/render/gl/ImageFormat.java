package com.github.jovialen.motor.render.gl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;

public enum ImageFormat {
    RGB, RGBA;

    public static ImageFormat fromSTBIChannels(int channels) {
        return switch (channels) {
            case STBImage.STBI_rgb -> RGB;
            case STBImage.STBI_rgb_alpha -> RGBA;
            default -> throw new IllegalStateException("Unexpected value: " + channels);
        };
    }

    public int toSTBIChannels() {
        return switch (this) {
            case RGB -> STBImage.STBI_rgb;
            case RGBA -> STBImage.STBI_rgb_alpha;
        };
    }

    public int toGl() {
        return switch (this) {
            case RGB -> GL20.GL_RGB;
            case RGBA -> GL20.GL_RGBA;
        };
    }
}
