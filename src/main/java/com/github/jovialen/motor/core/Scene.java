package com.github.jovialen.motor.core;

import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.render.RunRenderGraphTask;
import com.github.jovialen.motor.graph.scene.SceneRoot;

public class Scene {
    private final Application application;
    private final SceneRoot sceneRoot;
    private RenderRoot renderRoot;

    public Scene(Application application, SceneSource source) {
        this.application = application;
        this.sceneRoot = source.instantiate(new SceneRoot(application));
        this.renderRoot = new RenderRoot(application);
    }

    public void start() {
        sceneRoot.start();
        renderRoot.create();
    }

    public void update(double deltaTime) {
        application.getRenderThread().addTask(new RunRenderGraphTask(renderRoot));
        sceneRoot.process(deltaTime);

        application.getRenderThread().waitIdle();
    }

    public void stop() {
        renderRoot.destroy();
        sceneRoot.stop();
    }

    public Application getApplication() {
        return application;
    }

    public SceneRoot getSceneRoot() {
        return sceneRoot;
    }

    public RenderRoot getRenderRoot() {
        return renderRoot;
    }
}
