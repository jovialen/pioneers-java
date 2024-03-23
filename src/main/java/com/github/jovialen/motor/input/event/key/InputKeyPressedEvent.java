package com.github.jovialen.motor.input.event.key;

import com.github.jovialen.motor.window.Window;

public class InputKeyPressedEvent extends InputKeyEvent {
    public final boolean repeat;

    public InputKeyPressedEvent(Window window, int key, int scancode, int mods, boolean repeat) {
        super(window, key, scancode, mods);
        this.repeat = repeat;
    }
}
