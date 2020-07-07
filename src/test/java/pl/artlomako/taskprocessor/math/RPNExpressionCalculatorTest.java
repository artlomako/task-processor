package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RPNExpressionCalculatorTest {

    @ParameterizedTest
    @MethodSource("testCases")
    public void shouldComputeSimpleRPNExpression_whenExpressionHasOnlyFromOneNumber(
            String rpnExpression,
            BigDecimal expectedResult
    ) {
        // when
        BigDecimal result = RPNExpressionCalculator.calculate(rpnExpression);
        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.arguments("12", new BigDecimal("12")),
                Arguments.arguments("12 14 +", new BigDecimal("26")),
                Arguments.arguments("12 2 3 4 * 10 5 / + * +", new BigDecimal("40.00")),
                Arguments.arguments("12 888 1234 / 29834934 * + 999999999 747474 / +", new BigDecimal("21482502.32"))
        );
    }

}