package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OperatorUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"+", "-", "*", "/"})
    public void shouldReturnTrue_whenPassedOperator(String operator) {
        // when
        boolean result = OperatorUtils.isOperator(operator);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "aasd", "!@#$", " "})
    public void shouldReturnFalse_whenPassedOtherString(String operator) {
        // when
        boolean result = OperatorUtils.isOperator(operator);

        // then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"+", "-"})
    public void shouldReturnPrecedence0_whenOperatorHasLowerPrecedence(String operator) {
        // when
        int precedence = OperatorUtils.getOperatorPrecedence(operator);

        // then
        assertThat(precedence).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"*", "/"})
    public void shouldReturnPrecedence1_whenOperatorHasHigherPrecedence(String operator) {
        // when
        int precedence = OperatorUtils.getOperatorPrecedence(operator);

        // then
        assertThat(precedence).isOne();
    }

}