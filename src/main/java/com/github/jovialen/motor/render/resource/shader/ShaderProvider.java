package com.github.jovialen.motor.render.resource.shader;

import com.github.jovialen.motor.render.resource.ResourceProvider;
import com.github.jovialen.motor.render.resource.mesh.Vertex;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShaderProvider implements ResourceProvider<ShaderSource, ShaderProgram> {
    @Override
    public ShaderProgram provide(ShaderSource key) {
        if (key == null) {
            return new ShaderProgram(0, Vertex.LAYOUT);
        }

        ShaderProgram program = new ShaderProgram(key.getLayout());

        // Compiling shader modules
        List<ShaderModule> modules = new ArrayList<>(key.getShaderModules().size());
        for (ShaderModuleSource moduleSource : key.getShaderModules()) {
            ShaderModule module = new ShaderModule(moduleSource.stage());
            module.setSource(moduleSource.source());
            if (!module.compile()) {
                Logger.tag("GL").warn("Shader module failed to compile");
            }

            program.addShaderModule(module);
            modules.add(module);
        }

        // Link program
        if (!program.link()) {
            Logger.tag("GL").warn("Shader failed to link. Using default shader");
            program.destroy();
            program = new ShaderProgram(0, key.getLayout());
        }

        // Destroy modules (they are not required after linking)
        modules.forEach(ShaderModule::destroy);

        return program;
    }
}
