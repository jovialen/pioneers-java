package com.github.jovialen.motor.render.context;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class GLState implements AutoCloseable {
    private static GLState current = pushState();
    private final GLState parent;

    private final boolean unique;
    private int instances = 1;
    private int drawFrameBuffer = 0;
    private int readFrameBuffer = 0;
    private int program = 0;
    private int bufferArray = 0;
    private final Map<Integer, Integer> buffers = new HashMap<>();

    private GLState(boolean unique) {
        parent = current;
        current = this;
        this.unique = unique;

        // Copy current state from parent
        if (parent == null) return;
        drawFrameBuffer = parent.drawFrameBuffer;
        readFrameBuffer = parent.readFrameBuffer;
        program = parent.program;
        bufferArray = parent.bufferArray;
        buffers.putAll(parent.buffers);
    }

    public void bindFrameBuffer(int frameBuffer) {
        if (drawFrameBuffer == readFrameBuffer && drawFrameBuffer != frameBuffer) {
            drawFrameBuffer = readFrameBuffer = frameBuffer;
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        } else {
            bindReadFrameBuffer(frameBuffer);
            bindDrawFrameBuffer(frameBuffer);
        }
    }

    public void bindReadFrameBuffer(int frameBuffer) {
        if (readFrameBuffer == frameBuffer) return;
        this.readFrameBuffer = frameBuffer;
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer);
    }

    public void bindDrawFrameBuffer(int frameBuffer) {
        if (drawFrameBuffer == frameBuffer) return;
        this.drawFrameBuffer = frameBuffer;
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBuffer);
    }

    public void bindShaderProgram(int program) {
        if (this.program == program) return;
        this.program = program;
        GL20.glUseProgram(program);
    }

    public void bindBufferArray(int bufferArray) {
        if (this.bufferArray == bufferArray) return;
        this.bufferArray = bufferArray;
        GL30.glBindVertexArray(bufferArray);
    }

    public void bindVertexBuffer(int id) {
        bindBuffer(GL15.GL_ARRAY_BUFFER, id);
    }

    public void bindIndexBuffer(int id) {
        bindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void bindUniformBuffer(int id) {
        bindBuffer(GL40.GL_UNIFORM_BUFFER, id);
    }

    public void bindBuffer(int target, int id) {
        if (this.buffers.containsKey(target) && this.buffers.get(target) == id) return;
        this.buffers.put(target, id);
        GL15.glBindBuffer(target, id);
    }

    @Override
    public void close() {
        if (unique) {
            popState(this);
        }
        instances--;
        if (instances <= 0) {
            popState(this);
        }
    }

    public static GLState getCurrent() {
        return current;
    }

    public static GLState pushState() {
        return new GLState(true);
    }

    public static GLState pushSharedState() {
        if (current.unique) {
            return new GLState(false);
        }
        current.instances++;
        return current;
    }

    public static void popState(GLState state) {
        if (state != current) {
            Logger.tag("GL").error("Closing GL States out of order");
        }

        current = state.parent;
        state.bindReadFrameBuffer(current.readFrameBuffer);
        state.bindDrawFrameBuffer(current.drawFrameBuffer);
        state.bindShaderProgram(current.program);
        state.bindBufferArray(current.bufferArray);
        state.buffers.forEach((target, id) -> state.bindBuffer(target, current.buffers.getOrDefault(target, 0)));
    }
}
