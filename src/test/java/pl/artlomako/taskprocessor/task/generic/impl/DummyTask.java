package pl.artlomako.taskprocessor.task.generic.impl;

import pl.artlomako.taskprocessor.task.generic.Task;

public class DummyTask implements Task {
    private boolean executed = false;

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted() {
        this.executed = true;
    }
}
