package com.github.jovialen.motor.scene.nodes;

import com.github.jovialen.motor.render.Model;
import com.github.jovialen.motor.render.pipeline.Pipeline;
import com.github.jovialen.motor.scene.renderer.SceneRenderer;
import org.joml.Matrix4f;

public class ModelRenderNode extends Node3D {
    public Pipeline pipeline = Pipeline.DEFAULT;
    public Model model = new Model();

    private Matrix4f transform = new Matrix4f().identity();

    public ModelRenderNode() {}

    public ModelRenderNode(Model model) {
        this.model = model;
    }

    public ModelRenderNode(Pipeline pipeline, Model model) {
        this.pipeline = pipeline;
        this.model = model;
    }

    @Override
    public void sync() {
        super.sync();
        transform = getTransform(transform);
    }

    @Override
    public void submitToRender(SceneRenderer renderer) {
        super.submitToRender(renderer);
        renderer.addModel(pipeline, model, transform);
    }
}
