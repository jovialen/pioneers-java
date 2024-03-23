package com.github.jovialen.motor.input.event.key;

import com.github.jovialen.motor.input.event.InputEvent;
import com.github.jovialen.motor.window.Window;

public class InputCharEvent extends InputEvent {
    public final int codepoint;

    public InputCharEvent(Window window, int codepoint) {
        super(window);
        this.codepoint = codepoint;
    }
}
