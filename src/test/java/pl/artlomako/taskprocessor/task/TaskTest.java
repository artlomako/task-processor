package pl.artlomako.taskprocessor.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    public void completedFlagShouldBeFalse_whenNotExecuted() {
        // when
        Task task = TaskFactory.createDummy();
        //then
        boolean completed = task.isCompleted();
        assertFalse(completed);
    }

    @Test
    public void completedFlagShouldBeTrue_whenExecuted() {
        // given
        Task task = TaskFactory.createDummy();
        // when
        task.execute();
        //then
        boolean completed = task.isCompleted();
        assertTrue(completed);
    }
}