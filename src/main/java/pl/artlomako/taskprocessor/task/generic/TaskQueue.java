package pl.artlomako.taskprocessor.task.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue<T extends Task> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskQueue.class);

    private final LinkedList<T> tasks;
    private final Lock lock;
    private final Condition canReceiveTasks;
    private final Condition canGiveTasks;
    private final int itemsCountToResumeReceiving;
    private final int limit;

    public TaskQueue(int limit) {
        this.tasks = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.canReceiveTasks = this.lock.newCondition();
        this.canGiveTasks = this.lock.newCondition();
        this.itemsCountToResumeReceiving = limit / 2;
        this.limit = limit;
    }

    public void push(T task) throws InterruptedException {
        this.lock.lock();
        try {
            checkIfShouldStopReceiving();

            this.tasks.add(task);
            LOGGER.debug("Pushed task {}. Queue size: [{}]", task, this.tasks.size());

            continueGiving();
        } finally {
            this.lock.unlock();
        }
    }

    public T pop() throws InterruptedException {
        this.lock.lock();
        try {
            checkIfShouldStopGiving();

            T task = this.tasks.removeLast();
            LOGGER.debug("Popped task {}. Queue size: [{}]", task, this.tasks.size());

            checkIfShouldContinueReceiving();

            return task;
        } finally {
            this.lock.unlock();
        }
    }

    private void checkIfShouldStopReceiving() throws InterruptedException {
        while (this.tasks.size() == limit) {
            LOGGER.info("Task queue is full. Waiting");
            this.canReceiveTasks.await();
        }
    }

    private void continueGiving() {
        this.canGiveTasks.signal();
    }

    private void checkIfShouldStopGiving() throws InterruptedException {
        while (this.tasks.isEmpty()) {
            LOGGER.info("Task queue is empty. Waiting");
            this.canGiveTasks.await();
        }
    }

    private void checkIfShouldContinueReceiving() {
        if (this.tasks.size() == this.itemsCountToResumeReceiving) {
            LOGGER.info("Achieved tasks receiving limit. Resuming");
            this.canReceiveTasks.signal();
        }
    }

    public int size() {
        return this.tasks.size();
    }
}
