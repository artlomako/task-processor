package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfixToRPNExpressionConverterTest {

    @Test
    public void rpnAndInfixExpressionsShouldBeTheSame_whenInfixExpressionIsJustANumber(){
        // given
        String infixExpression = "42";
        // when
        String rpnExpression = InfixToRPNExpressionConverter.convert(infixExpression);
        // then
        assertEquals(infixExpression, rpnExpression);
    }

    @Test
    public void shouldConvertSimpleExpression_whenGivenInfixExpressionWithOnlyAdding(){
        // given
        String infixExpression = "42 + 43";
        String expectedRpnExpression = "42 43 +";
        // when
        String rpnExpression = InfixToRPNExpressionConverter.convert(infixExpression);
        // then
        assertEquals(expectedRpnExpression, rpnExpression);
    }

    @Test
    public void shouldConvertComplexExpression_whenGivenInfixExpressionWithAllAvailableValues(){
        // given
        String infixExpression = "42 + 43 + 44 * 45 / 46 + 47 - 48 - 49 * 50 + 51 / 52 / 53";
        String expectedRpnExpression = "42 43 + 44 45 * 46 / + 47 + 48 - 49 50 * - 51 52 / 53 / +";
        // when
        String rpnExpression = InfixToRPNExpressionConverter.convert(infixExpression);
        // then
        assertEquals(expectedRpnExpression, rpnExpression);
    }

    @Test
    public void shouldReturnEmptyExpression_whenEmptyExpressionGiven(){
        // given
        String infixExpression = "";
        String expectedRpnExpression = "";
        // when
        String rpnExpression = InfixToRPNExpressionConverter.convert(infixExpression);
        // then
        assertEquals(expectedRpnExpression, rpnExpression);
    }
}