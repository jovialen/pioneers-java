package com.github.jovialen.motor.render.resource;

import org.joml.Vector2i;

public interface Surface {
    Vector2i getResolution();

    default float getAspect() {
        Vector2i resolution = getResolution();
        return (float) resolution.x / (float) resolution.y;
    }

    int getId();
}
