package com.github.jovialen.motor.input.event;

import com.github.jovialen.motor.window.Window;
import com.github.jovialen.motor.window.event.WindowEvent;

public abstract class InputEvent extends WindowEvent {
    public InputEvent(Window window) {
        super(window);
    }
}
