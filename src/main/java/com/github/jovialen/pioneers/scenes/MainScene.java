package com.github.jovialen.pioneers.scenes;

import com.github.jovialen.motor.render.image.FileImage;
import com.github.jovialen.motor.render.image.Image;
import com.github.jovialen.motor.render.pipeline.FileShader;
import com.github.jovialen.motor.render.pipeline.Pipeline;
import com.github.jovialen.motor.render.gl.Shader;
import com.github.jovialen.motor.render.gl.ShaderProgram;
import com.github.jovialen.motor.render.mesh.Mesh;
import com.github.jovialen.motor.render.mesh.Vertex;
import com.github.jovialen.motor.scene.Scene;
import com.github.jovialen.motor.scene.SceneRoot;
import com.github.jovialen.motor.scene.nodes.Camera3DNode;
import com.github.jovialen.motor.scene.nodes.ModelRenderNode;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import java.nio.file.Path;
import java.util.List;

public class MainScene extends Scene {
    @Override
    public SceneRoot instantiate(SceneRoot root) {
        Camera3DNode camera = root.addChild(new Camera3DNode());
        camera.camera.clearColor = new Vector4f(1, 0, 0, 1);

        ModelRenderNode meshNode = root.addChild(new ModelRenderNode());

        Mesh mesh = new Mesh("Plane Mesh");
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector2f(0.0f, 0.0f)));
        mesh.vertices.add(new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector2f(1.0f, 1.0f)));
        mesh.vertices.add(new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector2f(0.0f, 1.0f)));
        mesh.vertices.add(new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector2f(1.0f, 0.0f)));
        mesh.indices = List.of(0, 3, 1, 1, 2, 0);
        meshNode.model.mesh = mesh;

        String shaders = Path.of("src", "main", "resources", "shaders").toString();
        meshNode.pipeline = new Pipeline(new FileShader(Path.of(shaders, "quad.vs.glsl")),
                new FileShader(Path.of(shaders, "quad.fs.glsl")));
        meshNode.pipeline.setBlending(true);

        String textures = Path.of("src", "main", "resources", "textures").toString();
        meshNode.model.images = new Image[]{ new FileImage(Path.of(textures, "opengl-logo.png")) };

        return root;
    }
}
