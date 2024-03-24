package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderRoot;

public class DestroyRenderGraphTask extends RenderGraphTask {
    public DestroyRenderGraphTask(RenderRoot renderRoot) {
        super(renderRoot);
    }

    @Override
    public void invoke() {
        renderRoot.destroy();
    }
}
