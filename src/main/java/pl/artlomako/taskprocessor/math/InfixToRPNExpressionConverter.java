package pl.artlomako.taskprocessor.math;

import java.util.LinkedList;

public class InfixToRPNExpressionConverter {
    private static final String TOKENS_SEPARATOR = " ";

    public static String convert(String infixExpression) {
        LinkedList<String> outputStack = new LinkedList<>();
        LinkedList<String> temporaryStack = new LinkedList<>();

        String[] tokens = infixExpression.split(TOKENS_SEPARATOR);
        for (String token : tokens) {
            handleToken(outputStack, temporaryStack, token);
        }
        moveRestOfTemporaryStackToOutput(outputStack, temporaryStack);

        return String.join(TOKENS_SEPARATOR, outputStack);
    }

    private static void handleToken(LinkedList<String> outputStack,
                                    LinkedList<String> temporaryStack,
                                    String token) {
        if (OperatorUtils.isOperator(token)) {
            handleOperator(outputStack, temporaryStack, token);
            return;
        }

        handleNumber(outputStack, token);
    }

    private static void moveRestOfTemporaryStackToOutput(LinkedList<String> outputStack,
                                                         LinkedList<String> temporaryStack) {
        while (!temporaryStack.isEmpty()) {
            outputStack.add(temporaryStack.removeLast());
        }
    }

    private static void handleOperator(LinkedList<String> outputStack,
                                       LinkedList<String> temporaryStack,
                                       String token) {
        int precedence = OperatorUtils.getOperatorPrecedence(token);
        while (!temporaryStack.isEmpty() && OperatorUtils.getOperatorPrecedence(temporaryStack.peekLast()) >= precedence) {
            outputStack.add(temporaryStack.removeLast());
        }
        temporaryStack.add(token);
    }

    private static void handleNumber(LinkedList<String> outputStack,
                                     String token) {
        outputStack.add(token);
    }
}
