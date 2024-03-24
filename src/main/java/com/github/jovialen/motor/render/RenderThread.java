package com.github.jovialen.motor.render;

import com.github.jovialen.motor.render.task.ActivateContextTask;
import com.github.jovialen.motor.render.task.DeactivateContextTask;
import com.github.jovialen.motor.thread.ThreadTask;
import com.github.jovialen.motor.thread.ThreadWorker;
import org.tinylog.Logger;

public class RenderThread {
    private final Renderer renderer;
    private final ThreadWorker threadWorker;

    public RenderThread(Renderer renderer) {
        this.renderer = renderer;
        this.threadWorker = new ThreadWorker();
    }

    public void start() {
        Logger.tag("RENDER").info("Starting render thread");

        threadWorker.start();
        resume();
    }

    public void resume() {
        threadWorker.addTask(new ActivateContextTask(renderer));
    }

    public void pause() {
        threadWorker.addTask(new DeactivateContextTask(renderer));
        threadWorker.waitIdle();
    }

    public void stop() {
        Logger.tag("RENDER").info("Stopping render thread");

        pause();

        threadWorker.stopWorking();;
        threadWorker.waitIdle();

        try {
            join();
        } catch (InterruptedException e) {
            Logger.tag("RENDER").error("Failed to join render thread: {}", e);
        }
    }

    public void waitIdle() {
        threadWorker.waitIdle();
    }

    public void addTask(ThreadTask task) {
        threadWorker.addTask(task);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public ThreadWorker getThreadWorker() {
        return threadWorker;
    }

    private void join() throws InterruptedException {
        threadWorker.join();
    }
}
