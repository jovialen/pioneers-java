package com.github.jovialen.motor.core;

import com.github.jovialen.motor.graph.render.RenderRoot;
import com.github.jovialen.motor.graph.scene.SceneRoot;

public class Scene {
    private final SceneRoot sceneRoot;
    private RenderRoot renderRoot;

    public Scene(Application application, SceneSource source) {
        this.sceneRoot = source.instantiate(new SceneRoot(application));
        this.renderRoot = new RenderRoot(application);
    }

    public void start() {
        sceneRoot.start();
        renderRoot.create();
    }

    public void update(double deltaTime) {
        sceneRoot.process(deltaTime);
        renderRoot.run();
    }

    public void stop() {
        renderRoot.destroy();
        sceneRoot.stop();
    }
}
