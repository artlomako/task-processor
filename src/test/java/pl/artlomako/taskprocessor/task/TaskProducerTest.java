package pl.artlomako.taskprocessor.task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.executeInThreads;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.waitForThreadsState;

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
        assertThat(queue.size()).isEqualTo(QUEUE_SIZE_LIMIT);
    }

    private List<Thread> executeProducerInMultipleThreads(TaskQueue queue) {
        TaskProducer taskProducer = new TaskProducer(queue, TaskFactory::createDummy);
        return executeInThreads(taskProducer::run, THREADS_COUNT);
    }
}