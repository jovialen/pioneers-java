package com.github.jovialen.motor.window.event;

import com.github.jovialen.motor.window.Window;

public class WindowFocusEvent extends WindowEvent {
    public final boolean focused;

    public WindowFocusEvent(Window window, boolean focused) {
        super(window);
        this.focused = focused;
    }
}
