package com.github.jovialen.motor.input.event.mouse;

import com.github.jovialen.motor.window.Window;

public class InputMouseEnterEvent extends InputMouseEvent {
    public final boolean entered;

    public InputMouseEnterEvent(Window window, boolean entered) {
        super(window);
        this.entered = entered;
    }
}
