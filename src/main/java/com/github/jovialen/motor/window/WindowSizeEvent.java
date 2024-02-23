package com.github.jovialen.motor.window;

import org.joml.Vector2i;

public class WindowSizeEvent extends WindowEvent {
    public final Vector2i size;
    public final Vector2i prevSize;

    public WindowSizeEvent(Window window, Vector2i size, Vector2i prevSize) {
        super(window);
        this.size = size;
        this.prevSize = prevSize;
    }
}
