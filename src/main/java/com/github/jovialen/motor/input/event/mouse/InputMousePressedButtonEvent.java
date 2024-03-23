package com.github.jovialen.motor.input.event.mouse;

import com.github.jovialen.motor.window.Window;

public class InputMousePressedButtonEvent extends InputMouseButtonEvent {
    public InputMousePressedButtonEvent(Window window, int button, int mods) {
        super(window, button, mods);
    }
}
