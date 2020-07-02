package pl.artlomako.taskprocessor.math;

import org.junit.jupiter.api.RepeatedTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InfixExpressionGeneratorTest {

    public static final Pattern NUMBER_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    @RepeatedTest(100)
    public void shouldGenerateCorrectNumber() {
        // when
        String randomNumber = InfixExpressionGenerator.getRandomNumber();
        // then
        boolean correctNumber = NUMBER_PATTERN.matcher(randomNumber).matches();
        assertTrue(correctNumber);

        int digitsCount = randomNumber.length();
        boolean correctDigitsCount = digitsCount < InfixExpressionGenerator.MAX_NUMBER_LENGTH;
        assertTrue(correctDigitsCount);
    }

    @RepeatedTest(100)
    public void shouldGenerateCorrectOperator() {
        // when
        String randomOperator = InfixExpressionGenerator.getRandomOperator();
        // then
        boolean correctOperator = OperatorUtils.isOperator(randomOperator);
        assertTrue(correctOperator);
    }

}