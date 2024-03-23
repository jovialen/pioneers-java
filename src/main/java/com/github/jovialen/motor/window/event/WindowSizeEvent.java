package com.github.jovialen.motor.window.event;

import com.github.jovialen.motor.window.Window;
import org.joml.Vector2i;

public class WindowSizeEvent extends WindowEvent {
    public final Vector2i size;

    public WindowSizeEvent(Window window, Vector2i size) {
        super(window);
        this.size = size;
    }
}
