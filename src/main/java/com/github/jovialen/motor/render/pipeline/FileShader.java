package com.github.jovialen.motor.render.pipeline;

import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileShader implements ShaderSource {
    private final Path path;

    public FileShader(Path path) {
        this.path = path;
    }

    @Override
    public String getSource() {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Logger.tag("RENDER").error("Failed to read shader source file: {}", e);
            return "";
        }
    }
}
