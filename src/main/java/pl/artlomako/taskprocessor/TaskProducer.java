package pl.artlomako.taskprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TaskProducer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);
    private final TaskQueue taskQueue;

    public TaskProducer(TaskQueue queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            pushTaskToQueue();
        }
    }

    public void pushTaskToQueue() {
        try {
            Task task = createTask();
            this.taskQueue.push(task);
        } catch (Exception e) {
            LOGGER.error("Exception occurred on producing task", e);
        }
    }

    private Task createTask() {
        LOGGER.info("Trying to produce task");
        String taskExpression = LocalDateTime.now().toString();
        Task task = new Task(taskExpression);
        LOGGER.info("Produced task {}", task);
        return task;
    }
}
