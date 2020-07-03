package pl.artlomako.taskprocessor.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class RPNExpressionCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RPNExpressionCalculator.class);
    private static final String TOKENS_SEPARATOR = " ";

    public static BigDecimal calculate(String rpnExpression) {
        LOGGER.info("Calculating RPN expression [{}]", rpnExpression);
        String[] tokens = rpnExpression.split(TOKENS_SEPARATOR);
        LinkedList<BigDecimal> resultStack = new LinkedList<>();
        for (String token : tokens) {
            handleToken(resultStack, token);
        }
        BigDecimal result = resultStack.get(0);
        LOGGER.info("RPN expression calculated [{}]. Result: [{}]", rpnExpression, result);
        return result;
    }

    private static void handleToken(LinkedList<BigDecimal> resultStack, String token) {
        if (OperatorUtils.isOperator(token)) {
            handleOperator(resultStack, token);
            return;
        }
        resultStack.add(new BigDecimal(token));
    }

    private static void handleOperator(LinkedList<BigDecimal> resultStack, String token) {
        BigDecimal lastNumber = resultStack.removeLast();
        BigDecimal numberBeforeLast = resultStack.removeLast();
        BigDecimal resultOfOperation = applyOperator(numberBeforeLast, lastNumber, token);
        resultStack.add(resultOfOperation);
    }

    private static BigDecimal applyOperator(BigDecimal firstNumber, BigDecimal secondNumber, String operator) {
        BiFunction<BigDecimal, BigDecimal, BigDecimal> function = OperatorUtils.getOperatorFunction(operator);
        return function.apply(firstNumber, secondNumber);
    }
}
