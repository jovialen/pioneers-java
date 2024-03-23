package com.github.jovialen.motor.window.event;

import com.github.jovialen.motor.window.Window;

public abstract class WindowEvent {
    public final Window window;

    public WindowEvent(Window window) {
        this.window = window;
    }
}
