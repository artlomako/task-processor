package pl.artlomako.taskprocessor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumer.class);
    private final TaskQueue taskQueue;

    public TaskConsumer(TaskQueue queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            consumeFromQueue();
        }
    }

    public void consumeFromQueue() {
        try {
            LOGGER.debug("Trying to consume task");
            Task task = this.taskQueue.pop();
            LOGGER.debug("Consumed task {}", task);
            task.execute();
        } catch (Exception e) {
            LOGGER.error("Exception occurred on consuming task", e);
        }
    }
}
