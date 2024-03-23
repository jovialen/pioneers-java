package com.github.jovialen.motor.input.event.key;

import com.github.jovialen.motor.window.Window;

public class InputKeyReleasedEvent extends InputKeyEvent {
    public InputKeyReleasedEvent(Window window, int key, int scancode, int mods) {
        super(window, key, scancode, mods);
    }
}
