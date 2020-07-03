package pl.artlomako.taskprocessor.task;

import pl.artlomako.taskprocessor.math.InfixExpressionCalculator;
import pl.artlomako.taskprocessor.math.InfixExpressionGenerator;

public class TaskFactory {
    public static Task createWithRandomInfixExpression() {
        String expression = InfixExpressionGenerator.generate();
        return new Task(
                expression,
                () -> InfixExpressionCalculator.calculate(expression)
        );
    }

    public static Task createDummy() {
        return new Task("", () -> {

        });
    }
}
