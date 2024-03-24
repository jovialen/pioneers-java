package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.thread.ThreadTask;

public abstract class RenderGraphTask implements ThreadTask {
    protected final RenderRoot renderRoot;

    public RenderGraphTask(RenderRoot renderRoot) {
        this.renderRoot = renderRoot;
    }
}
