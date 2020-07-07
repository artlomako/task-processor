package pl.artlomako.taskprocessor.task.generic.impl;

import pl.artlomako.taskprocessor.task.generic.TaskConsumer;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;

public class DummyTaskConsumer extends TaskConsumer<DummyTask> {
    public DummyTaskConsumer(TaskQueue<DummyTask> queue) {
        super(queue);
    }

    @Override
    public void handleTask(DummyTask task) {
        task.setExecuted();
    }
}
