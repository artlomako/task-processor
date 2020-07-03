package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RPNExpressionCalculatorTest {

    @Test
    public void shouldComputeSimpleRPNExpression_whenExpressionHasOnlyFromOneNumber() {
        // given
        String rpnExpression = "12";
        BigDecimal expectedResult = BigDecimal.valueOf(12);
        // when
        BigDecimal result = RPNExpressionCalculator.calculate(rpnExpression);
        // then
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldComputeSimpleRPNExpression_whenExpressionHasOnlyOneOperator() {
        // given
        String rpnExpression = "12 14 +";
        BigDecimal expectedResult = BigDecimal.valueOf(26);
        // when
        BigDecimal result = RPNExpressionCalculator.calculate(rpnExpression);
        // then
        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldComputeComplexRPNExpression_whenExpressionHasOnlyOneOperator() {
        // given
        String rpnExpression = "12 2 3 4 * 10 5 / + * +";
        BigDecimal expectedResult = new BigDecimal("40.00");
        // when
        BigDecimal result = RPNExpressionCalculator.calculate(rpnExpression);
        // then
        assertEquals(expectedResult, result);
    }

}