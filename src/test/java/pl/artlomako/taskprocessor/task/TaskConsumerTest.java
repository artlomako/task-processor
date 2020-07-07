package pl.artlomako.taskprocessor.task;

import org.junit.jupiter.api.Test;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;
import pl.artlomako.taskprocessor.task.generic.impl.DummyTask;
import pl.artlomako.taskprocessor.task.generic.impl.DummyTaskConsumer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.executeInThreads;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.waitForThreadsState;

class TaskConsumerTest {
    private static final int QUEUE_SIZE_LIMIT = 9;
    private static final int THREADS_COUNT = 5;

    @Test
    public void shouldConsumeAndExecuteAllTasksFromQueue() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = new TaskQueue<>(QUEUE_SIZE_LIMIT);
        List<DummyTask> tasksInQueue = new ArrayList<>();
        for (int i = 0; i < QUEUE_SIZE_LIMIT / 2; i++) {
            DummyTask task = new DummyTask();
            queue.push(task);
            tasksInQueue.add(task);
        }

        // when
        List<Thread> consumerThreads = executeConsumerInMultipleThreads(queue);

        // then
        waitForThreadsState(consumerThreads, Thread.State.WAITING);
        assertThat(queue.size()).isZero();

        boolean allTasksCompleted = tasksInQueue.stream()
                .allMatch(DummyTask::isExecuted);

        assertThat(allTasksCompleted).isTrue();
    }


    private List<Thread> executeConsumerInMultipleThreads(TaskQueue<DummyTask> queue) {
        DummyTaskConsumer taskConsumer = new DummyTaskConsumer(queue);
        return executeInThreads(taskConsumer::run, THREADS_COUNT);
    }
}