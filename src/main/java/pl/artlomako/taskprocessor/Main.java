package pl.artlomako.taskprocessor;


import pl.artlomako.taskprocessor.task.calculateexpression.CalculateExpressionTask;
import pl.artlomako.taskprocessor.task.calculateexpression.CalculateExpressionTaskConsumer;
import pl.artlomako.taskprocessor.task.calculateexpression.CalculateExpressionTaskProducer;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int QUEUE_SIZE_LIMIT = 1000;
    private static final int PRODUCERS_COUNT = 2;
    private static final int CONSUMERS_COUNT = 4;

    public static void main(String[] args) {
        TaskQueue<CalculateExpressionTask> taskQueue = new TaskQueue<>(QUEUE_SIZE_LIMIT);

        ExecutorService producersPool = Executors.newFixedThreadPool(PRODUCERS_COUNT);
        ExecutorService consumersPool = Executors.newFixedThreadPool(CONSUMERS_COUNT);

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            producersPool.submit(new CalculateExpressionTaskProducer(taskQueue));
        }

        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            consumersPool.submit(new CalculateExpressionTaskConsumer(taskQueue));
        }
    }
}
