package pl.artlomako.taskprocessor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

    private final String name;
    private final Runnable body;

    private boolean completed = false;

    public Task(String name, Runnable body) {
        this.name = name;
        this.body = body;
    }

    public void execute() {
        LOGGER.debug("Executing task {}", this);
        this.body.run();
        this.completed = true;
        LOGGER.debug("Task execution completed {}", this);
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                '}';
    }
}
