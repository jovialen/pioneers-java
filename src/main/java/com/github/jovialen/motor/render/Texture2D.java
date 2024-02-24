package com.github.jovialen.motor.render;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.tinylog.Logger;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

public class Texture2D {
    public static final ImageFormat INTERNAL_FORMAT = ImageFormat.RGBA;

    private final int id;
    private final String debugName;

    public Texture2D() {
        this("");
    }

    public Texture2D(String debugName) {
        Logger.tag("GL").info("Creating texture {}", debugName);
        this.debugName = debugName;
        id = GL20.glGenTextures();
    }

    public void destroy() {
        Logger.tag("GL").info("Destroying texture {}", debugName);
        GL20.glDeleteTextures(id);
    }

    public void bind() {
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
    }

    public void store(Path path) {
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer pixels = STBImage.stbi_load(path.toString(), x, y, channels, 0);
        Vector2i size = new Vector2i(x.get(), y.get());
        ImageFormat format = ImageFormat.fromSTBIChannels(channels.get());
        store(pixels, size, format);
    }

    public void store(ByteBuffer pixels, Vector2i size, ImageFormat format) {
        bind();
        GL20.glTexImage2D(GL20.GL_TEXTURE_2D,
                0,
                INTERNAL_FORMAT.toGl(),
                size.x, size.y,
                0,
                format.toGl(),
                GL20.GL_UNSIGNED_BYTE,
                pixels);
        GL30.glGenerateMipmap(GL20.GL_TEXTURE_2D);
        unbind();
    }

    public int getId() {
        return id;
    }

    public String getDebugName() {
        return debugName;
    }

    @Override
    public String toString() {
        return ("Texture " + debugName).trim();
    }
}
