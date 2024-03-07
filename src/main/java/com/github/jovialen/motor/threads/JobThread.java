package com.github.jovialen.motor.threads;

import org.tinylog.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A thread that can queue tasks to perform.
 */
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

        Logger.tag("THREAD").info("Thread worker stopped");
    }

    /**
     * Stop working and exit the thread as soon as possible.
     */
    public void stopWorking() {
        Logger.tag("THREAD").debug("Stopping thread worker");
        working = false;
        synchronized (tasks) {
            tasks.notifyAll();
        }
    }

    /**
     * Add a task to be carried out.
     * @param task Task to queue.
     */
    public void addTask(ThreadTask task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notifyAll();
        }
    }

    /**
     * Wait until the thread worker has no more tasks to perform.
     */
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
