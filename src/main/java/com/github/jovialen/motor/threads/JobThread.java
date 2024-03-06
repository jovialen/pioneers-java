package com.github.jovialen.motor.threads;

import org.tinylog.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

public class JobThread extends Thread {
    private boolean working = true;
    private boolean executingTask = false;
    private final Queue<ThreadTask> tasks = new ArrayDeque<>();

    @Override
    public void run() {
        Logger.tag("THREAD").info("Starting thread worker");

        while (working) {
            ThreadTask task = getTask();
            executeTask(task);
        }

        Logger.tag("THREAD").info("Thread worker exited");
    }

    public void stopWorking() {
        working = false;
        synchronized (tasks) {
            tasks.notifyAll();
        }
    }

    public void addTask(ThreadTask task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notifyAll();
        }
    }

    public void waitIdle() {
        synchronized (tasks) {
            while (!tasks.isEmpty() || executingTask) {
                waitNotified();
            }
        }
    }

    private ThreadTask getTask() {
        synchronized (tasks) {
            while (tasks.isEmpty() && working) {
                waitNotified();
            }

            if (working) {
                return tasks.poll();
            } else {
                return null;
            }
        }
    }

    private void waitNotified() {
        synchronized (tasks) {
            try {
                tasks.wait();
            } catch (InterruptedException e) {
                Logger.tag("THREAD").error("Failed to wait for task: {}", e);
            }
        }
    }

    private void executeTask(ThreadTask task) {
        if (task == null) return;

        executingTask = true;
        task.invoke();
        executingTask = false;

        synchronized (tasks) {
            tasks.notifyAll();
        }
    }
}
