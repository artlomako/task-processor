package pl.artlomako.taskprocessor;


import pl.artlomako.taskprocessor.task.TaskConsumer;
import pl.artlomako.taskprocessor.task.TaskFactory;
import pl.artlomako.taskprocessor.task.TaskProducer;
import pl.artlomako.taskprocessor.task.TaskQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int QUEUE_SIZE_LIMIT = 1000;
    private static final int PRODUCERS_COUNT = 2;
    private static final int CONSUMERS_COUNT = 4;

    public static void main(String[] args) {
        ExecutorService producersPool = Executors.newFixedThreadPool(PRODUCERS_COUNT);
        ExecutorService consumersPool = Executors.newFixedThreadPool(CONSUMERS_COUNT);
        TaskQueue taskQueue = new TaskQueue(QUEUE_SIZE_LIMIT);
        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            producersPool.submit(new TaskProducer(taskQueue, TaskFactory::createWithRandomInfixExpression));
        }

        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            consumersPool.submit(new TaskConsumer(taskQueue));
        }
    }
}
