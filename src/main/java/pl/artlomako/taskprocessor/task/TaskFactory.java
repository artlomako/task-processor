package pl.artlomako.taskprocessor.task;

public class TaskFactory {
    public static Task createDummy() {
        return new Task("", () -> {

        });
    }
}
