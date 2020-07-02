package pl.artlomako.taskprocessor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.*;

class TaskProducerTest {
    private static final int QUEUE_SIZE_LIMIT = 9;
    private static final int THREADS_COUNT = 5;

    @Test
    public void shouldProduceTasksAndFillTheQueue() {
        // given
        TaskQueue queue = new TaskQueue(QUEUE_SIZE_LIMIT);
        // when
        List<Thread> producerThreads = executeProducerInMultipleThreads(queue);
        // then
        waitForThreadsState(producerThreads, Thread.State.WAITING);
        assertEquals(QUEUE_SIZE_LIMIT, queue.size());
    }

    private List<Thread> executeProducerInMultipleThreads(TaskQueue queue) {
        TaskProducer taskProducer = new TaskProducer(queue);
        return executeInThreads(taskProducer::run, THREADS_COUNT);
    }
}