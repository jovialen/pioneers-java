package com.github.jovialen.motor.render;

import com.github.jovialen.motor.utils.gl.GLBoolean;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Shader {
    private final int id;
    private final String debugName;
    private final int type;

    private boolean compiled = false;

    public Shader(int type) {
        this("", type);
    }

    public Shader(String debugName, int type) {
        this(debugName, type, null);
    }

    public Shader(String debugName, int type, String source) {
        Logger.tag("GL").info("Creating shader {}", debugName);
        this.debugName = debugName;
        this.type = type;
        id = GL20.glCreateShader(type);
        if (source != null) {
            setSource(source);
            compile();
        }
    }

    public void destroy() {
        Logger.tag("GL").info("Destroying shader {}", debugName);
        GL20.glDeleteShader(id);
    }

    public void setSource(Path path) {
        try {
            String source = Files.readString(path);
            setSource(source);
        } catch (IOException e) {
            Logger.error("Failed to set shader source: {}", e);
        }
    }

    public void setSource(String source) {
        GL20.glShaderSource(id, source);
    }

    public boolean compile() {
        GL20.glCompileShader(id);
        GLBoolean compileStatus = new GLBoolean(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS));
        if (!compileStatus.get()) {
            String log = GL20.glGetShaderInfoLog(id);
            Logger.tag("GL").error("Failed to compile shader{}: {}",
                    (" " + debugName).trim(),
                    log);
        }
        compiled = compileStatus.get();
        return compiled;
    }

    public int getId() {
        return id;
    }

    public String getDebugName() {
        return debugName;
    }

    @Override
    public String toString() {
        return ("Shader " + debugName).trim();
    }

    public int getType() {
        return type;
    }

    public boolean isCompiled() {
        return compiled;
    }
}
