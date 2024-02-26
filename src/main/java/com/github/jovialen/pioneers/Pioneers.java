package com.github.jovialen.pioneers;

import com.github.jovialen.motor.Motor;
import com.github.jovialen.motor.core.Application;

public class Pioneers extends Application {
    public static final String NAME = "Pioneers";

    public Pioneers() {
        super(NAME);
        window.setFullscreen(false);
    }

    public static void main(String[] args) {
        Motor.init();

        Pioneers app = new Pioneers();
        app.run();

        Motor.shutdown();
    }
}
