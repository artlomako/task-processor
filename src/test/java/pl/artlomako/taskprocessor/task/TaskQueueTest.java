package pl.artlomako.taskprocessor.task;

import org.junit.jupiter.api.Test;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;
import pl.artlomako.taskprocessor.task.generic.impl.DummyTask;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.artlomako.taskprocessor.ConcurrencyTestUtils.*;

class TaskQueueTest {

    private static final int QUEUE_SIZE_LIMIT = 9;

    @Test
    public void threadShouldWaitAfterPopExecution_whenQueueIsEmpty() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = createEmptyQueue();
        // when
        Thread popThread = executeInThreadWaitingForLatches(queue::pop, 1, 0);
        // then
        waitForThreadState(
                popThread,
                Thread.State.WAITING
        );
        assertThat(queue.size()).isZero();
    }

    @Test
    public void oneThreadShouldBeWaitingAfterPopExecution_whenPoppingByMoreThreadsThanElementsInQueue() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = createQueueWithElements();

        // when
        List<Thread> threads = executeInThreadsWaitingForLatches(queue::pop,
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT,
                QUEUE_SIZE_LIMIT - 1);

        // then
        waitForThreadsState(threads, Thread.State.TERMINATED, QUEUE_SIZE_LIMIT);
        waitForThreadsState(threads, Thread.State.WAITING, 1);
        assertThat(queue.size()).isZero();
    }


    @Test
    public void pushThreadShouldBeWaiting_whenSizeLimitReached() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = createEmptyQueue();

        // when
        List<Thread> pushThreads = executeInThreadsWaitingForLatches(
                () -> queue.push(createTask()),
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT
        );

        // then
        waitForThreadsState(pushThreads, Thread.State.TERMINATED, QUEUE_SIZE_LIMIT);
        waitForThreadsState(pushThreads, Thread.State.WAITING, 1);
        assertThat(queue.size()).isEqualTo(QUEUE_SIZE_LIMIT);
    }

    @Test
    public void pushThreadShouldContinue_whenSizeLimitReachedThenConsumedHalfOfItems() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = createEmptyQueue();

        // when
        // execute push threads
        List<Thread> pushThreads = executeInThreadsWaitingForLatches(
                () -> queue.push(createTask()),
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT
        );

        // then
        waitForThreadsState(pushThreads, Thread.State.TERMINATED, QUEUE_SIZE_LIMIT);
        waitForThreadsState(pushThreads, Thread.State.WAITING, 1);
        assertThat(queue.size()).isEqualTo(QUEUE_SIZE_LIMIT);

        // execute pop threads
        List<Thread> popThreads = executeInThreadsWaitingForLatches(queue::pop,
                QUEUE_SIZE_LIMIT / 2 + 1,
                QUEUE_SIZE_LIMIT / 2,
                QUEUE_SIZE_LIMIT / 2
        );

        // then
        waitForThreadsState(popThreads, Thread.State.TERMINATED);
        waitForThreadsState(pushThreads, Thread.State.TERMINATED);
        assertThat(queue.size()).isEqualTo(QUEUE_SIZE_LIMIT / 2 + 1);
    }

    @Test
    public void popThreadShouldContinue_whenQueueBecameEmptyThenElementAdded() throws InterruptedException {
        // given
        TaskQueue<DummyTask> queue = createQueueWithElements();

        // when
        // pop threads
        List<Thread> popThreads = executeInThreadsWaitingForLatches(
                queue::pop,
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT + 1,
                QUEUE_SIZE_LIMIT
        );

        // then
        waitForThreadsState(popThreads, Thread.State.TERMINATED, QUEUE_SIZE_LIMIT);
        waitForThreadsState(popThreads, Thread.State.WAITING, 1);
        assertThat(queue.size()).isZero();

        // push thread
        Thread pushTask = executeInThread(() -> queue.push(createTask()));

        // then
        waitForThreadState(pushTask, Thread.State.TERMINATED);
        waitForThreadsState(popThreads, Thread.State.TERMINATED);
        assertThat(queue.size()).isZero();
    }

    private TaskQueue<DummyTask> createQueueWithElements() throws InterruptedException {
        TaskQueue<DummyTask> taskQueue = createEmptyQueue();
        for (int i = 0; i < QUEUE_SIZE_LIMIT; i++) {
            DummyTask task = createTask();
            taskQueue.push(task);
        }
        return taskQueue;
    }

    private TaskQueue<DummyTask> createEmptyQueue() {
        return new TaskQueue<>(QUEUE_SIZE_LIMIT);
    }

    private DummyTask createTask() {
        return new DummyTask();
    }
}