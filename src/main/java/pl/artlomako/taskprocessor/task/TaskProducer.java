package pl.artlomako.taskprocessor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class TaskProducer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);
    private final TaskQueue taskQueue;
    private final Supplier<Task> createTask;

    public TaskProducer(TaskQueue queue, Supplier<Task> createTask) {
        this.taskQueue = queue;
        this.createTask = createTask;
    }

    @Override
    public void run() {
        while (true) {
            pushTaskToQueue();
        }
    }

    public void pushTaskToQueue() {
        try {
            Task task = this.createTask.get();
            this.taskQueue.push(task);
        } catch (Exception e) {
            LOGGER.error("Exception occurred on producing task", e);
        }
    }

}
