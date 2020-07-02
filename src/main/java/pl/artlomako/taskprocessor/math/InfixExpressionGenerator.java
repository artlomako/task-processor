package pl.artlomako.taskprocessor.math;

import java.util.concurrent.ThreadLocalRandom;

public class InfixExpressionGenerator {
    private static final int MAX_EXPRESSION_LENGTH = 200;
    static final int MAX_NUMBER_LENGTH = 10;

    public static String generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        StringBuilder result = new StringBuilder(MAX_EXPRESSION_LENGTH);
        String firstNumber = getRandomNumber();
        result.append(firstNumber);

        boolean generateNumber = false;
        while (true) {
            String nextToken = generateNumber ? getRandomNumber() : getRandomOperator();
            result.append(" ");
            result.append(nextToken);

            boolean shouldStop = generateNumber && (result.length() > MAX_EXPRESSION_LENGTH || !random.nextBoolean());
            if (shouldStop) {
                break;
            }

            generateNumber = !generateNumber;
        }
        return result.toString();
    }

    static String getRandomNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder result = new StringBuilder();

        int firstDigit = random.nextInt(1, 10);
        result.append(firstDigit);

        while (result.length() < MAX_NUMBER_LENGTH - 1 && random.nextBoolean()) {
            int digit = random.nextInt(0, 10);
            result.append(digit);
        }

        return result.toString();
    }

    static String getRandomOperator() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int operatorsCount = OperatorUtils.SUPPORTED_OPERATORS.size();
        int operatorIndex = random.nextInt(operatorsCount);
        return OperatorUtils.SUPPORTED_OPERATORS.get(operatorIndex);
    }
}
