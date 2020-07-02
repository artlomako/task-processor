package pl.artlomako.taskprocessor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void completedFlagShouldBeFalse_whenNotExecuted() {
        // when
        Task task = new Task("");
        //then
        boolean completed = task.isCompleted();
        assertFalse(completed);
    }

    @Test
    public void completedFlagShouldBeTrue_whenExecuted() {
        // given
        Task task = new Task("");
        // when
        task.execute();
        //then
        boolean completed = task.isCompleted();
        assertTrue(completed);
    }
}