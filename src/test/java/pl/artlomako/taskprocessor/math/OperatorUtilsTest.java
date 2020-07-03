package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperatorUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"+", "-", "*", "/"})
    public void shouldReturnTrue_whenPassedOperator(String operator) {
        // when
        boolean result = OperatorUtils.isOperator(operator);
        // then
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "aasd", "!@#$", " "})
    public void shouldReturnFalse_whenPassedOtherString(String operator) {
        // when
        boolean result = OperatorUtils.isOperator(operator);
        // then
        Assertions.assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"+", "-"})
    public void shouldReturnPrecedence0_whenOperatorHasLowerPrecedence(String operator) {
        // when
        int precedence = OperatorUtils.getOperatorPrecedence(operator);
        // then
        assertEquals(0, precedence);
    }

    @ParameterizedTest
    @ValueSource(strings = {"*", "/"})
    public void shouldReturnPrecedence1_whenOperatorHasHigherPrecedence(String operator) {
        // when
        int precedence = OperatorUtils.getOperatorPrecedence(operator);
        // then
        assertEquals(1, precedence);
    }

}