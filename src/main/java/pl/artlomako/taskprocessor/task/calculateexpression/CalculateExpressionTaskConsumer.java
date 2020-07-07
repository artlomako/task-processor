package pl.artlomako.taskprocessor.task.calculateexpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.artlomako.taskprocessor.math.InfixExpressionCalculator;
import pl.artlomako.taskprocessor.task.generic.TaskConsumer;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;

import java.math.BigDecimal;

public class CalculateExpressionTaskConsumer extends TaskConsumer<CalculateExpressionTask> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateExpressionTaskConsumer.class);

    public CalculateExpressionTaskConsumer(TaskQueue<CalculateExpressionTask> queue) {
        super(queue);
    }

    @Override
    public void handleTask(CalculateExpressionTask task) {
        String infixExpression = task.getInfixExpression();
        BigDecimal calculationResult = InfixExpressionCalculator.calculate(infixExpression);
        LOGGER.info("Calculation result of expression {}: {}", infixExpression, calculationResult);
    }
}
