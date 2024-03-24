package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;

import java.util.Map;

public abstract class MigrationTask extends RenderGraphTask {
    protected final SceneRoot sceneRoot;
    protected final Map<SceneNode, RenderNode> migrationMap;

    public MigrationTask(RenderRoot renderRoot, SceneRoot sceneRoot, Map<SceneNode, RenderNode> migrationMap) {
        super(renderRoot);
        this.sceneRoot = sceneRoot;
        this.migrationMap = migrationMap;
    }
}
