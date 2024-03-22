package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.render.Camera;
import com.github.jovialen.motor.scene.renderer.SceneRenderer;
import com.github.jovialen.motor.window.events.WindowSizeEvent;
import com.google.common.eventbus.Subscribe;
import org.joml.Matrix4f;

public class Camera3DNode extends Node3D {
    public Camera camera = new Camera();

    private final Camera renderCamera = new Camera();
    private Matrix4f cameraTransform = new Matrix4f();

    @Override
    public void start() {
        super.start();
        getRoot().getEventBus().register(this);
    }

    @Override
    public void sync() {
        super.sync();

        // Update the render camera
        renderCamera.set(camera);

        // Update render position
        cameraTransform = getGlobalTransform(cameraTransform.identity());
    }

    @Override
    public void submitToRender(SceneRenderer renderer) {
        super.submitToRender(renderer);
        renderer.addCamera(camera, cameraTransform);
    }

    @Subscribe
    public void onWindowResize(WindowSizeEvent event) {
        camera.setAspect(event.size);
        camera.updateProjection();
    }
}
