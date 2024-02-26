package com.github.jovialen.motor.render.gl;

import com.github.jovialen.motor.utils.gl.GLDebugSeverity;
import com.github.jovialen.motor.utils.gl.GLDebugSource;
import com.github.jovialen.motor.utils.gl.GLDebugType;
import com.github.jovialen.motor.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class GLContext {
    private static class TinyLogGLDebugMessageCallback extends GLDebugMessageCallback {
        @Override
        public void invoke(int glSource,
                           int glType,
                           int id,
                           int glSeverity,
                           int length,
                           long glMessage,
                           long userParam) {
            GLDebugSource source = GLDebugSource.fromGl(glSource);
            GLDebugType type = GLDebugType.fromGl(glType);
            GLDebugSeverity severity = GLDebugSeverity.fromGl(glSeverity);
            String message = getMessage(length, glMessage);

            String format = "OpenGL {} {}: Severity {}: {}";
            TaggedLogger logger = Logger.tag("GL");
            if (type == GLDebugType.ERROR || severity == GLDebugSeverity.HIGH) {
                logger.error(format, source, type, severity, message);
            } else if (severity == GLDebugSeverity.MEDIUM) {
                logger.warn(format, source, type, severity, message);
            } else if (severity == GLDebugSeverity.LOW) {
                logger.debug(format, source, type, severity, message);
            } else {
                logger.trace(format, source, type, severity, message);
            }
        }
    }

    public static final int VERSION_MAJOR = 4;
    public static final int VERSION_MINOR = 3;

    private final Window contextWindow;
    private final boolean debug;
    private final GLCapabilities glCapabilities;

    public GLContext(Window contextWindow) {
        Logger.tag("GL").info("Creating OpenGL Context");

        this.contextWindow = contextWindow;
        this.debug = contextWindow.isDebug();

        GLFW.glfwMakeContextCurrent(contextWindow.getHandle());
        glCapabilities = GL.createCapabilities();

        if (debug) {
            Logger.tag("GL").debug("Configuring OpenGL debug message callback");
            GL43.glDebugMessageCallback(new TinyLogGLDebugMessageCallback(), MemoryUtil.NULL);
            GL43.glDebugMessageControl(GLDebugSource.all(),
                    GLDebugType.all(),
                    GLDebugSeverity.all(),
                    0,
                    true);
        }
    }

    public void activate() {
        GLFW.glfwMakeContextCurrent(contextWindow.getHandle());
    }

    public Window getContextWindow() {
        return contextWindow;
    }

    public GLCapabilities getGlCapabilities() {
        return glCapabilities;
    }

    public boolean isDebug() {
        return debug;
    }
}
