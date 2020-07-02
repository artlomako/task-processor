package pl.artlomako.taskprocessor.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class OperatorUtils {
    public static final List<String> SUPPORTED_OPERATORS = Arrays.asList("+", "-", "*", "/");

    public static boolean isOperator(String token) {
        return SUPPORTED_OPERATORS.contains(token);
    }

    public static BiFunction<BigDecimal, BigDecimal, BigDecimal> getOperatorFunction(String operator) {
        return switch (operator) {
            case "+" -> BigDecimal::add;
            case "-" -> BigDecimal::subtract;
            case "*" -> BigDecimal::multiply;
            case "/" -> (BigDecimal firstNumber, BigDecimal secondNumber) ->
                    firstNumber.divide(secondNumber, 2, RoundingMode.HALF_UP);
            default -> throw new IllegalArgumentException(String.format("Unsupported operator [%s]", operator));
        };
    }

    public static int getOperatorPrecedence(String operator) {
        if ("*".equals(operator) || "/".equals(operator)) {
            return 1;
        }
        return 0;
    }
}
