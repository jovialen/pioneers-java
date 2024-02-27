package com.github.jovialen.motor.render.image;

import com.github.jovialen.motor.render.gl.ImageFormat;
import com.github.jovialen.motor.render.gl.Texture2D;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.tinylog.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class NetImage extends Image {
    private final URL url;

    public NetImage(URL url) {
        this("", url);
    }

    public NetImage(String debugName, URL url) {
        super(debugName);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public static NetImage create(String address) {
        try {
            URL url = URI.create(address).toURL();
            return new NetImage(url);
        } catch (MalformedURLException e) {
            Logger.tag("RENDER").error("Failed to create net image: {}", e);
            return null;
        }
    }

    @Override
    protected void load(Texture2D texture2D) {
        ByteBuffer image = loadUrl(url);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        if (!STBImage.stbi_info_from_memory(image, width, height, channels)) {
            Logger.tag("RENDER").error("Failed to get image info: {}", STBImage.stbi_failure_reason());
            return;
        }

        ByteBuffer pixels = STBImage.stbi_load_from_memory(image, width, height, channels, STBImage.STBI_default);
        Vector2i size = new Vector2i(width.get(), height.get());
        ImageFormat format = ImageFormat.fromSTBIChannels(channels.get());

        texture2D.store(pixels, size, format);
    }

    private ByteBuffer loadUrl(URL url) {
        Logger.tag("RENDER").debug("Loading image from URL {}", url);
        ByteBuffer buffer = BufferUtils.createByteBuffer(1024 * 8);
        try {
            ReadableByteChannel in = Channels.newChannel(url.openStream());
            int bytes = 0;
            while (bytes != -1) {
                bytes = in.read(buffer);
                if (buffer.remaining() == 0) {
                    ByteBuffer next = BufferUtils.createByteBuffer(buffer.capacity() * 2);
                    buffer.flip();
                    next.put(buffer);
                    buffer = next;
                }
            }
        } catch (IOException e) {
            Logger.tag("RENDER").error("Failed to read image from url: {}", url);
        }
        buffer.flip();
        return buffer;
    }
}
