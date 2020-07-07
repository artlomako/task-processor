package pl.artlomako.taskprocessor.task.calculateexpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.artlomako.taskprocessor.math.InfixExpressionGenerator;
import pl.artlomako.taskprocessor.task.generic.TaskProducer;
import pl.artlomako.taskprocessor.task.generic.TaskQueue;

public class CalculateExpressionTaskProducer extends TaskProducer<CalculateExpressionTask> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateExpressionTaskProducer.class);

    public CalculateExpressionTaskProducer(TaskQueue<CalculateExpressionTask> queue) {
        super(queue);
    }

    @Override
    public CalculateExpressionTask createTask() {
        String infixExpression = InfixExpressionGenerator.generate();
        return new CalculateExpressionTask(infixExpression);
    }
}
