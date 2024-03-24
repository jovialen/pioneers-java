package com.github.jovialen.motor.graph.render;

import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.thread.ThreadTask;

public class RunRenderGraphTask implements ThreadTask {
    private final RenderRoot renderRoot;

    public RunRenderGraphTask(RenderRoot renderRoot) {
        this.renderRoot = renderRoot;
    }

    @Override
    public void invoke() {
        renderRoot.run();
    }
}
