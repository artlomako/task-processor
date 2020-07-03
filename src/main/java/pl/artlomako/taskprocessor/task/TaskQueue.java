package pl.artlomako.taskprocessor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskQueue.class);

    private final LinkedList<Task> tasks;
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

    public void push(Task task) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.tasks.size() == limit) {
                LOGGER.info("Task queue is full. Waiting");
                this.canReceiveTasks.await();
            }

            this.tasks.add(task);
            LOGGER.debug("Pushed task {}. Queue size: [{}]", task, this.tasks.size());

            this.canGiveTasks.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public Task pop() throws InterruptedException {
        this.lock.lock();
        try {
            while (this.tasks.isEmpty()) {
                LOGGER.info("Task queue is empty. Waiting");
                this.canGiveTasks.await();
            }

            Task task = this.tasks.removeLast();
            LOGGER.debug("Popped task {}. Queue size: [{}]", task, this.tasks.size());

            if (this.tasks.size() == this.itemsCountToResumeReceiving) {
                LOGGER.info("Achieved tasks receiving limit. Resuming");
                this.canReceiveTasks.signal();
            }

            return task;
        } finally {
            this.lock.unlock();
        }
    }

    public int size() {
        return this.tasks.size();
    }
}
