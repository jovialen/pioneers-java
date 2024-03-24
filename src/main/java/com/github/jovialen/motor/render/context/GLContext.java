package com.github.jovialen.motor.render.context;

import com.github.jovialen.motor.utils.GLFWUtils;
import com.github.jovialen.motor.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

import java.nio.IntBuffer;

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

    public GLContext(Window contextWindow, boolean debug) {
        Logger.tag("GL").info("Creating OpenGL Context ({})", debug ? "debug" : "not debug");

        this.contextWindow = contextWindow;
        this.debug = debug;

        GLFW.glfwMakeContextCurrent(contextWindow.getHandle());
        glCapabilities = GL.createCapabilities();

        if (debug) {
            setupDebugMessageCallback();
        }

        deactivate();
    }

    public void activate() {
        Logger.tag("RENDER").info("Activating render context");
        GLFW.glfwMakeContextCurrent(contextWindow.getHandle());
        GL.setCapabilities(glCapabilities);
    }

    public void deactivate() {
        Logger.tag("RENDER").info("Deactivating render context");
        GL.setCapabilities(null);
        GLFW.glfwMakeContextCurrent(MemoryUtil.NULL);
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

    private void setupDebugMessageCallback() {
        Logger.tag("GL").debug("Configuring OpenGL debug message callback");

        int flags = GL11.glGetInteger(GL30.GL_CONTEXT_FLAGS);
        if ((flags & GL43.GL_CONTEXT_FLAG_DEBUG_BIT) == 0) {
            Logger.tag("GL").error("Failed to create debug message callback; Not a debug context");
            return;
        }

        GL11.glEnable(GL43.GL_DEBUG_OUTPUT);
        GL11.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);

        GL43.glDebugMessageCallback(new TinyLogGLDebugMessageCallback(), MemoryUtil.NULL);
        GL43.glDebugMessageControl(GLDebugSource.all(),
                GLDebugType.all(),
                GLDebugSeverity.all(),
                (IntBuffer) null,
                true);
    }

    public static void configureGLFW(boolean debug) {
        // Profile
        int forwardCompatibility = GLFWUtils.bool(Platform.get() == Platform.MACOSX);
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_OPENGL_API);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, forwardCompatibility);

        // Version
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, VERSION_MAJOR);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, VERSION_MINOR);

        // Debug
        int glfwDebug = GLFWUtils.bool(debug);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_DEBUG, glfwDebug);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, glfwDebug);
    }
}
