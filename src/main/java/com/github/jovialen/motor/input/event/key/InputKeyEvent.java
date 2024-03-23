package com.github.jovialen.motor.input.event.key;

import com.github.jovialen.motor.input.event.InputEvent;
import com.github.jovialen.motor.window.Window;

public class InputKeyEvent extends InputEvent {
    public final int key;
    public final int scancode;
    public final int mods;

    public InputKeyEvent(Window window, int key, int scancode, int mods) {
        super(window);
        this.key = key;
        this.scancode = scancode;
        this.mods = mods;
    }
}
