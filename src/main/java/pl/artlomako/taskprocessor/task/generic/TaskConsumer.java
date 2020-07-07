package pl.artlomako.taskprocessor.task.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskConsumer<T extends Task> implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumer.class);
    private final TaskQueue<T> taskQueue;

    public TaskConsumer(TaskQueue<T> queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                LOGGER.debug("Trying to consume task");
                T task = this.taskQueue.pop();
                handleTask(task);
            } catch (Exception e) {
                LOGGER.error("Exception occurred on consuming task", e);
            }
        }
    }

    public abstract void handleTask(T task);
}
