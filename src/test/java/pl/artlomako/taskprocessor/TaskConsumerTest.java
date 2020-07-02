package pl.artlomako.taskprocessor;

import com.google.code.tempusfugit.temporal.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.*;

class TaskConsumerTest {
    private static final int QUEUE_SIZE_LIMIT = 9;
    private static final int THREADS_COUNT = 5;

    @Test
    public void shouldConsumeAndExecuteAllTasksFromQueue() throws InterruptedException {
        // given
        TaskQueue queue = new TaskQueue(QUEUE_SIZE_LIMIT);
        List<Task> tasksInQueue = new ArrayList<>();
        for (int i = 0; i < QUEUE_SIZE_LIMIT / 2; i++) {
            Task task = createTask();
            queue.push(task);
            tasksInQueue.add(task);
        }
        // when
        List<Thread> consumerThreads = executeConsumerInMultipleThreads(queue);
        // then
        waitForThreadsState(consumerThreads, Thread.State.WAITING);
        assertEquals(0, queue.size());

        boolean allTasksCompleted = tasksInQueue.stream()
                .allMatch(Task::isCompleted);
        assertTrue(allTasksCompleted);
    }


    private Task createTask() {
        return new Task("");
    }

    private List<Thread> executeConsumerInMultipleThreads(TaskQueue queue) {
        TaskConsumer taskConsumer = new TaskConsumer(queue);
        return executeInThreads(taskConsumer::run, THREADS_COUNT);
    }
}