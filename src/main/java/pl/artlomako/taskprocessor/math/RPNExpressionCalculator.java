package pl.artlomako.taskprocessor.math;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class RPNExpressionCalculator {

    private static final String TOKENS_SEPARATOR = " ";

    public static BigDecimal calculate(String rpnExpression) {
        String[] tokens = rpnExpression.split(TOKENS_SEPARATOR);
        LinkedList<BigDecimal> resultStack = new LinkedList<>();
        for (String token : tokens) {
            handleToken(resultStack, token);
        }
        return resultStack.get(0);
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
