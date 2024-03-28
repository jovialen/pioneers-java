package com.github.jovialen.motor.render.resource.layout.uniform;

import com.github.jovialen.motor.render.resource.layout.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniformLayout {
    public static final UniformLayout EMPTY = new UniformLayout();

    private final List<UniformAttribute> attributes = new ArrayList<>();

    public void addUniform(String name, DataType dataType) {
        attributes.add(new UniformAttribute(name, dataType));
    }

    public List<UniformAttribute> getAttributes() {
        return attributes;
    }

    public boolean hasAttribute(String name) {
        return getAttribute(name) != null;
    }

    public DataType getAttribute(String name) {
        for (UniformAttribute attribute : attributes) {
            if (Objects.equals(attribute.name(), name)) {
                return attribute.type();
            }
        }
        return null;
    }
}
