package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfixExpressionCalculatorTest {

    @Test
    public void shouldReturnNumberFromExpression_whenExpressionIsJustANumber() {
        // given
        String expression = "42";
        BigDecimal expectedResult = BigDecimal.valueOf(42);
        // when
        BigDecimal result = InfixExpressionCalculator.calculate(expression);
        // then
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturnResultOfComputation() {
        // given
        String expression = "42 + 43 + 44 * 45 / 46 + 47 - 48 - 49 * 50 + 51 / 52 / 53";
        BigDecimal expectedResult = new BigDecimal("-2322.94");
        // when
        BigDecimal result = InfixExpressionCalculator.calculate(expression);
        // then
        assertEquals(expectedResult, result);
    }

}