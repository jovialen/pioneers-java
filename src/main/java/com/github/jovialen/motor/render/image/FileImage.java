package com.github.jovialen.motor.render.image;

import com.github.jovialen.motor.render.gl.ImageFormat;
import com.github.jovialen.motor.render.gl.Texture2D;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

public class FileImage extends Image {
    private final Path path;

    public FileImage(Path path) {
        this("", path);
    }

    public FileImage(String debugName, Path path) {
        super(debugName);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    protected void load(Texture2D texture2D) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer pixels = STBImage.stbi_load(path.toString(), width, height, channels, STBImage.STBI_default);
        Vector2i size = new Vector2i(width.get(), height.get());
        ImageFormat format = ImageFormat.fromSTBIChannels(channels.get());

        texture2D.store(pixels, size, format);
    }
}
