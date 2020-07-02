package pl.artlomako.taskprocessor;

import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public interface ConcurrencyTestUtils {

    static void waitForThreadState(
            Thread thread,
            Thread.State expectedState) {
        waitForThreadsState(
                Collections.singletonList(thread),
                expectedState,
                1
        );
    }

    static void waitForThreadsState(
            List<Thread> threads,
            Thread.State expectedState,
            int expectedThreadsCount) {
        Duration oneSecondTimeout = Duration.ofSeconds(1);

        assertTimeoutPreemptively(oneSecondTimeout, () -> {
            while (true) {
                long threadsInExpectedStateCount = threads.stream()
                        .map(Thread::getState)
                        .filter(expectedState::equals)
                        .count();
                if (threadsInExpectedStateCount == expectedThreadsCount) {
                    return;
                }
            }
        });
    }

    static void waitForThreadsState(
            List<Thread> threads,
            Thread.State expectedState) {
        waitForThreadsState(threads, expectedState, threads.size());
    }

    static Thread executeInThreadWaitingForLatches(Executable executable,
                                                   int beforeLatchCount,
                                                   int afterLatchCount) throws InterruptedException {
        return executeInThreadsWaitingForLatches(executable, 1, beforeLatchCount, afterLatchCount)
                .get(0);
    }

    static List<Thread> executeInThreadsWaitingForLatches(Executable executable,
                                                          int threadsCount,
                                                          int beforeLatchCount,
                                                          int afterLatchCount) throws InterruptedException {
        CountDownLatch beforeLatch = new CountDownLatch(beforeLatchCount);
        CountDownLatch afterLatch = new CountDownLatch(afterLatchCount);
        List<Thread> threads = executeInThreadsBetweenLatches(executable, threadsCount, beforeLatch, afterLatch);
        beforeLatch.await();
        afterLatch.await();
        return threads;
    }

    static List<Thread> executeInThreadsBetweenLatches(Executable executable,
                                                       int threadsCount,
                                                       CountDownLatch beforeLatch,
                                                       CountDownLatch afterLatch) {
        return executeInThreads(
                wrapBetweenLatches(
                        executable,
                        beforeLatch,
                        afterLatch
                ),
                threadsCount
        );
    }

    static List<Thread> executeInThreads(Executable executable, int threadsCount) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = executeInThread(executable);
            threads.add(thread);
        }
        return threads;
    }

    static Thread executeInThread(Executable executable) {
        Thread thread = new Thread(() -> {
            try {
                executable.execute();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });

        thread.start();
        return thread;
    }

    static Executable wrapBetweenLatches(Executable executable,
                                         CountDownLatch beforeLatch,
                                         CountDownLatch afterLatch) {
        return () -> {
            try {
                beforeLatch.countDown();
                executable.execute();
                afterLatch.countDown();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
    }
}
