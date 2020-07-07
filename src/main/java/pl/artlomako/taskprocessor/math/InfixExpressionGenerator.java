package pl.artlomako.taskprocessor.math;

import java.util.concurrent.ThreadLocalRandom;

public class InfixExpressionGenerator {
    static final int MAX_NUMBER_LENGTH = 10;
    private static final int MAX_EXPRESSION_LENGTH = 200;
    private static final String TOKENS_SEPARATOR = " ";

    public static String generate() {
        StringBuilder result = new StringBuilder(MAX_EXPRESSION_LENGTH);

        String firstNumber = getRandomNumber();
        result.append(firstNumber);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        boolean generateNumber = false;
        while (true) {
            String nextToken = generateNumber ? getRandomNumber() : getRandomOperator();
            result.append(TOKENS_SEPARATOR);
            result.append(nextToken);

            boolean maxLengthExceeded = result.length() > MAX_EXPRESSION_LENGTH;
            boolean shouldStop = generateNumber && (maxLengthExceeded || !random.nextBoolean());
            if (shouldStop) {
                break;
            }

            generateNumber = !generateNumber;
        }

        return result.toString();
    }

    static String getRandomNumber() {
        StringBuilder result = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int numberLength = random.nextInt(1, MAX_NUMBER_LENGTH);

        int firstDigit = generateFirstDigit(random);
        result.append(firstDigit);

        for (int i = 0; i < numberLength; i++) {
            int randomDigit = generateRandomDigit(random);
            result.append(randomDigit);
        }

        return result.toString();
    }

    static String getRandomOperator() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int operatorsCount = OperatorUtils.SUPPORTED_OPERATORS.size();
        int operatorIndex = random.nextInt(operatorsCount);
        return OperatorUtils.SUPPORTED_OPERATORS.get(operatorIndex);
    }

    private static int generateFirstDigit(ThreadLocalRandom random) {
        return random.nextInt(1, 10);
    }

    private static int generateRandomDigit(ThreadLocalRandom random) {
        return random.nextInt(0, 10);
    }

}
