package com.github.jovialen.motor.input.event.mouse;

import com.github.jovialen.motor.window.Window;
import org.joml.Vector2d;

public class InputMouseMoveEvent extends InputMouseEvent {
    public final Vector2d position;

    public InputMouseMoveEvent(Window window, Vector2d position) {
        super(window);
        this.position = position;
    }
}
