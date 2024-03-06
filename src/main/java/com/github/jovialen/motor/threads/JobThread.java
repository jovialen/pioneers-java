package com.github.jovialen.motor.threads;

import org.tinylog.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

public class JobThread extends Thread {
    private boolean working = true;
    private final Queue<ThreadTask> tasks = new ArrayDeque<>();

    @Override
    public void run() {
        Logger.tag("APP").info("Starting thread worker");

        while (working) {
            ThreadTask task = getTask();
            executeTask(task);
        }

        Logger.tag("APP").info("Thread worker exited");
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
            while (!tasks.isEmpty()) {
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
                Logger.tag("APP").error("Failed to wait for task: {}", e);
            }
        }
    }

    private void executeTask(ThreadTask task) {
        if (task == null) return;

        task.invoke();
        synchronized (tasks) {
            tasks.notifyAll();
        }
    }
}
