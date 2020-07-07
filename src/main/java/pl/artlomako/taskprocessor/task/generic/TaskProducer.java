package pl.artlomako.taskprocessor.task.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TaskProducer<T extends Task> implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);
    private final TaskQueue<T> taskQueue;

    public TaskProducer(TaskQueue<T> queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                T task = createTask();
                taskQueue.push(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract T createTask();

}
