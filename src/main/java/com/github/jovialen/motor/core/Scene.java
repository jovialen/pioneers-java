package com.github.jovialen.motor.core;

import com.github.jovialen.motor.graph.render.RenderNode;
import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.render.task.CreateRenderGraphTask;
import com.github.jovialen.motor.graph.render.task.DestroyRenderGraphTask;
import com.github.jovialen.motor.graph.render.task.RunRenderGraphTask;
import com.github.jovialen.motor.graph.render.task.SyncRenderGraphTask;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.event.SceneNodeEvent;
import com.github.jovialen.motor.graph.scene.renderable.MigrationNode;
import com.github.jovialen.motor.render.RenderThread;
import com.google.common.eventbus.Subscribe;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {
    private final Application application;
    private final RenderThread renderThread;
    private final SceneRoot sceneRoot;
    private final Map<SceneNode, RenderNode> migrationMap = new HashMap<>();
    private final List<SceneNode> toRemove = new ArrayList<>();
    private RenderRoot renderRoot = null;
    private boolean rebuildRenderGraph = true;

    public Scene(Application application, SceneSource source) {
        this.application = application;
        this.renderThread = application.getRenderThread();
        this.sceneRoot = source.instantiate(new SceneRoot(application));

        application.getEventBus().register(this);
    }

    public void start() {
        sceneRoot.start();
        shouldRebuildRenderGraph();
    }

    public void update(double deltaTime) {
        // Check if the scene graph has to be updated
        if (rebuildRenderGraph) {
            rebuildRenderGraph();
        }

        // Synchronize render graph with the scene graph
        renderThread.addTask(new SyncRenderGraphTask(renderRoot, sceneRoot, migrationMap));
        renderThread.waitIdle();

        // Start the render
        renderThread.addTask(new RunRenderGraphTask(renderRoot));

        // Do the scene logic at the same time
        sceneRoot.process(deltaTime);

        // Wait for the render to be complete
        application.getRenderThread().waitIdle();

        // Remove unwanted nodes
        while (!toRemove.isEmpty()) {
            SceneNode node = toRemove.removeFirst();
            Logger.tag("SCENE").debug("Removing node {}", node);
            node.getParent().removeChild(node);
        }
    }

    public void stop() {
        destroyRenderGraph();
        sceneRoot.stop();
    }

    public void shouldRebuildRenderGraph() {
        rebuildRenderGraph = true;
    }

    public void removeNode(SceneNode node) {
        toRemove.add(node);
    }

    public Application getApplication() {
        return application;
    }

    public RenderThread getRenderThread() {
        return renderThread;
    }

    public List<SceneNode> getToRemove() {
        return toRemove;
    }

    public SceneRoot getSceneRoot() {
        return sceneRoot;
    }

    public RenderRoot getRenderRoot() {
        return renderRoot;
    }

    @Subscribe
    public void onSceneNodeEvent(SceneNodeEvent event) {
        // Rebuild the render graph if the node added or removed from the
        // render graph is a migration node
        if (MigrationNode.class.isAssignableFrom(event.node.getClass())) {
            shouldRebuildRenderGraph();
        }
    }

    private void rebuildRenderGraph() {
        Logger.tag("RENDER").debug("Rebuilding render graph");
        rebuildRenderGraph = false;

        if (renderRoot != null) {
            destroyRenderGraph();
        }

        migrationMap.clear();
        renderRoot = new RenderRoot(application);
        renderThread.addTask(new CreateRenderGraphTask(renderRoot, sceneRoot, migrationMap));
    }

    private void destroyRenderGraph() {
        renderThread.addTask(new DestroyRenderGraphTask(renderRoot));
        renderRoot = null;
    }
}
