package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.MigrationNode;

import java.util.List;
import java.util.Map;

public class SyncRenderGraphTask extends MigrationTask {
    public SyncRenderGraphTask(RenderRoot renderRoot, SceneRoot sceneRoot, Map<SceneNode, RenderNode> migrationMap) {
        super(renderRoot, sceneRoot, migrationMap);
    }

    @Override
    public void invoke() {
        //noinspection rawtypes
        List<MigrationNode> migrationNodes = sceneRoot.getChildren(MigrationNode.class);
        for (MigrationNode<?> migrationNode : migrationNodes) {
            migrationNode.dumbSynchronize(migrationMap.get((SceneNode) migrationNode));
        }
    }
}
