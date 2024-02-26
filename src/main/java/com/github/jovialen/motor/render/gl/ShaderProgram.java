package com.github.jovialen.motor.render.gl;

import com.github.jovialen.motor.utils.gl.GLBoolean;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;

public class ShaderProgram {
    public static final ShaderProgram DEFAULT = new ShaderProgram("Default Program", 0);

    private final int id;
    private final String debugName;
    private boolean finished = false;

    public ShaderProgram() {
        this("");
    }

    public ShaderProgram(String debugName) {
        Logger.tag("GL").info("Creating shader program {}", debugName);
        this.debugName = debugName;
        id = GL20.glCreateProgram();
    }

    private ShaderProgram(String debugName, int id) {
        this.debugName = debugName;
        this.id = id;
        finished = true;
    }

    public void destroy() {
        Logger.tag("GL").info("Destroying shader program {}", debugName);
        GL20.glDeleteProgram(id);
    }

    public void use() {
        if (finished) {
            GL20.glUseProgram(id);
        } else {
            Logger.tag("GL").warn("Attempting to use unfinished shader program");
        }
    }

    public void attach(Shader... shaders) {
        for (Shader shader : shaders) {
            attach(shader);
        }
    }

    public void attach(Shader shader) {
        if (shader.isCompiled()) {
            GL20.glAttachShader(id, shader.getId());
        } else {
            Logger.tag("GL").warn("Attempting to attach un-compiled {} to shader program {}",
                    shader, debugName);
        }
    }

    public boolean finish() {
        GL20.glLinkProgram(id);
        GLBoolean linkStatus = new GLBoolean(GL20.glGetProgrami(id, GL20.GL_LINK_STATUS));
        if (!linkStatus.get()) {
            String log = GL20.glGetProgramInfoLog(id);
            Logger.tag("GL").error("Failed to finish shader program{}: {}",
                    (" " + debugName).trim(),
                    log);
        }
        finished = linkStatus.get();
        return finished;
    }

    public int getId() {
        return id;
    }

    public String getDebugName() {
        return debugName;
    }

    @Override
    public String toString() {
        return ("Shader Program " + debugName).trim();
    }

    public boolean isFinished() {
        return finished;
    }
}
