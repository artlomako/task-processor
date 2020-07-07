package pl.artlomako.taskprocessor.task.generic.impl;

import pl.artlomako.taskprocessor.task.generic.TaskProducer;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;

public class DummyTaskProducer extends TaskProducer<DummyTask> {
    public DummyTaskProducer(TaskQueue<DummyTask> queue) {
        super(queue);
    }

    @Override
    public DummyTask createTask() {
        return new DummyTask();
    }
}
