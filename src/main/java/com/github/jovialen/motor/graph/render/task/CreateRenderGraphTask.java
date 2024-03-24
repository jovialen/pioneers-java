package com.github.jovialen.motor.graph.render.task;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.MigrationNode;
import org.tinylog.Logger;

import java.util.List;
import java.util.Map;

public class CreateRenderGraphTask extends MigrationTask {
    public CreateRenderGraphTask(RenderRoot renderRoot, SceneRoot sceneRoot, Map<SceneNode, RenderNode> migrationMap) {
        super(renderRoot, sceneRoot, migrationMap);
    }

    @Override
    public void invoke() {
        migrateNodeChildren(renderRoot, sceneRoot);
        renderRoot.create();
    }

    private void migrateNodeChildren(RenderNode parent, SceneNode source) {
        //noinspection rawtypes
        List<MigrationNode> migrationNodes = source.getChildren(MigrationNode.class,
                Integer.MAX_VALUE,
                false,
                true);

        for (MigrationNode<?> migrationNode : migrationNodes) {
            RenderNode child = parent.addChild(migrationNode.migrate(parent));
            migrationMap.put((SceneNode) migrationNode, child);
            migrateNodeChildren(child, (SceneNode) migrationNode);
        }
    }
}
