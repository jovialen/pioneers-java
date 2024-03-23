package com.github.jovialen.motor.input.event.mouse;

import com.github.jovialen.motor.window.Window;

public class InputMouseReleasedButtonEvent extends InputMouseButtonEvent {
    public InputMouseReleasedButtonEvent(Window window, int button, int mods) {
        super(window, button, mods);
    }
}
