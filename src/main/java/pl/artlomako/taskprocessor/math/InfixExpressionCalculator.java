package pl.artlomako.taskprocessor.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class InfixExpressionCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfixExpressionCalculator.class);

    public static BigDecimal calculate(String infixExpression) {
        LOGGER.debug("Calculating infix expression [{}]", infixExpression);
        String rpnExpression = InfixToRPNExpressionConverter.convert(infixExpression);
        BigDecimal result = RPNExpressionCalculator.calculate(rpnExpression);
        LOGGER.info("Infix expression calculated [{}]. Result: [{}]", infixExpression, result);
        return result;
    }
}
