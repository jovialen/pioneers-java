package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.core.SceneSource;
import com.github.jovialen.motor.graph.scene.SceneNode;
import com.github.jovialen.motor.graph.scene.SceneRoot;
import com.github.jovialen.motor.graph.scene.renderable.CameraNode;
import com.github.jovialen.motor.input.event.key.InputKeyReleasedEvent;
import com.google.common.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;

public class RemovableCameraScene implements SceneSource {
    public static class RemoveLocalSceneNode extends SceneNode {
        public RemoveLocalSceneNode(SceneNode parent) {
            super(parent);
            getRoot().getEventBus().register(this);
        }

        @Override
        public void stop() {
            super.stop();
            getRoot().getEventBus().unregister(this);
        }

        @Subscribe
        public void onKeyInput(InputKeyReleasedEvent event) {
            if (event.key == GLFW.GLFW_KEY_ESCAPE) {
                getLocalRoot().queueRemove();
            }
        }
    }

    @Override
    public SceneRoot instantiate(SceneRoot root) {
        CameraNode cameraNode = root.addChild(new CameraNode(root));
        cameraNode.clearColor.y = 0;
        cameraNode.clearColor.z = 0;

        root.addChild(new RemoveLocalSceneNode(root));

        return root;
    }
}
