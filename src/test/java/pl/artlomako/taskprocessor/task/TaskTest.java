package pl.artlomako.taskprocessor.task;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class TaskTest {

    @Test
    public void completedFlagShouldBeFalse_whenNotExecuted() {
        // when
        Task task = TaskFactory.createDummy();

        //then
        boolean completed = task.isCompleted();
        Assertions.assertThat(completed).isFalse();
    }

    @Test
    public void completedFlagShouldBeTrue_whenExecuted() {
        // given
        Task task = TaskFactory.createDummy();

        // when
        task.execute();

        //then
        boolean completed = task.isCompleted();
        Assertions.assertThat(completed).isTrue();
    }
}