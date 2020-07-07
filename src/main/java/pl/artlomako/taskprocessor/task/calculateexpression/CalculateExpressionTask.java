package pl.artlomako.taskprocessor.task.calculateexpression;

import pl.artlomako.taskprocessor.task.generic.Task;

public class CalculateExpressionTask implements Task {
    private final String infixExpression;

    public CalculateExpressionTask(String infixExpression) {
        this.infixExpression = infixExpression;
    }

    public String getInfixExpression() {
        return infixExpression;
    }
}
