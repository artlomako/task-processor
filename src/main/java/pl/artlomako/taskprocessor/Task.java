package pl.artlomako.taskprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    private final String expression;
    private boolean completed = false;

    public Task(String expression) {
        this.expression = expression;
    }

    public void execute() {
        LOGGER.info("Executing task {}", this);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "expression='" + expression + '\'' +
                '}';
    }
}
