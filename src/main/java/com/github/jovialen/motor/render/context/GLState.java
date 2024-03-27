package com.github.jovialen.motor.render.context;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class GLState implements AutoCloseable {
    private static GLState current = new GLState();
    private final GLState parent;

    private int drawFrameBuffer = 0;
    private int readFrameBuffer = 0;
    private int bufferArray = 0;
    private final Map<Integer, Integer> buffers = new HashMap<>();

    public GLState() {
        parent = current;
        current = this;
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

    public void bindBuffer(int target, int id) {
        if (this.buffers.get(target) == id) return;
        this.buffers.put(target, id);
        GL15.glBindBuffer(target, id);
    }

    @Override
    public void close() {
        if (current.parent != parent) {
            Logger.tag("GL").error("Closing GL States out of order");
        }

        current = parent;
        bindReadFrameBuffer(current.readFrameBuffer);
        bindDrawFrameBuffer(current.drawFrameBuffer);
        bindBufferArray(current.bufferArray);
        this.buffers.forEach((target, id) -> bindBuffer(target, current.buffers.getOrDefault(target, 0)));
    }

    public static GLState getCurrent() {
        return current;
    }
}
