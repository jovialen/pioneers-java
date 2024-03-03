package com.github.jovialen.pioneers;

import com.github.jovialen.motor.Motor;
import com.github.jovialen.motor.core.Application;
import com.github.jovialen.pioneers.scenes.MainScene;

public class Pioneers extends Application {
    public static final String NAME = "Pioneers";

    public Pioneers() {
        super(NAME);
        window.setFullscreen(false);
        setScene(new MainScene());
    }

    public static void main(String[] args) {
        Motor.init();

        Pioneers app = new Pioneers();
        app.run();

        Motor.shutdown();
    }
}
