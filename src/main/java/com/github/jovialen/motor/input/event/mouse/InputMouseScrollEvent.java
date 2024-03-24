package com.github.jovialen.motor.input.event.mouse;

import com.github.jovialen.motor.input.event.mouse.InputMouseEvent;
import com.github.jovialen.motor.window.Window;
import org.joml.Vector2d;

public class InputMouseScrollEvent extends InputMouseEvent {
    public final Vector2d scroll;

    public InputMouseScrollEvent(Window window, Vector2d scroll) {
        super(window);
        this.scroll = scroll;
    }
}
