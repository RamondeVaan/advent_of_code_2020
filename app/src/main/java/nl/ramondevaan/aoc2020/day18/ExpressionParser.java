package nl.ramondevaan.aoc2020.day18;

import lombok.Value;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Value
public class ExpressionParser implements Parser<String, Expression> {

    Map<ExpressionType, Integer> precedence;

    @Override
    public Expression parse(String toParse) {
        Deque<Expression> stack = new ArrayDeque<>();
        AtomicInteger currentIndex = new AtomicInteger(1);

        while (currentIndex.get() < toParse.length()) {
            parseNext(toParse, currentIndex, stack);
        }

        Expression topExpression = stack.pop();
        Expression parent;

        while ((parent = topExpression.getParent()) != null) {
            topExpression = parent;
        }

        return topExpression;
    }

    private void parseNext(String toParse, AtomicInteger currentIndex, Deque<Expression> stack) {
        char current = toParse.charAt(currentIndex.get() - 1);

        while (currentIndex.get() < toParse.length() && Character.isWhitespace(current)) {
            current = toParse.charAt(currentIndex.getAndIncrement());
        }
        if (Character.isWhitespace(current)) {
            return;
        }

        if (Character.isDigit(current)) {
            parseNumber(toParse, currentIndex, stack, current);
        } else if (current == '+') {
            parseAddition(toParse, currentIndex, stack);
        } else if (current == '*') {
            parseMultiplication(toParse, currentIndex, stack);
        }
    }

    private void parseNumber(String toParse, AtomicInteger currentIndex, Deque<Expression> stack, char current) {
        StringBuilder numberBuilder = new StringBuilder();

        while (currentIndex.get() < toParse.length() && Character.isDigit(current)) {
            numberBuilder.append(current);
            current = toParse.charAt(currentIndex.getAndIncrement());
        }
        if (Character.isDigit(current)) {
            numberBuilder.append(current);
        }

        NumberExpression expression = new NumberExpression(Long.parseLong(numberBuilder.toString()));

        Expression top = stack.pollFirst();
        if (top != null) {
            expression.setParent(top);
            top.addRight(expression);
        }
        stack.push(expression);
    }

    private void parseAddition(String toParse, AtomicInteger currentIndex, Deque<Expression> stack) {
        setBinaryOperator(new AdditionExpression(), currentIndex, stack);
    }

    private void parseMultiplication(String toParse, AtomicInteger currentIndex, Deque<Expression> stack) {
        setBinaryOperator(new MultiplicationExpression(), currentIndex, stack);
    }

    private void setBinaryOperator(Expression expression, AtomicInteger currentIndex, Deque<Expression> stack) {
        int expressionPrecedence = precedence.get(expression.getType());

        Expression lastExpression = stack.pop();
        int lastPrecedence = precedence.get(lastExpression.getType());

        if (lastPrecedence >= expressionPrecedence) {
            Expression parent = lastExpression.getParent();

            while (parent != null && precedence.get(parent.getType()) >= expressionPrecedence) {
                lastExpression = parent;
                parent = lastExpression.getParent();
            }
            if (parent != null) {
                parent.addRight(expression);
                expression.setParent(parent);
            }

            lastExpression.setParent(expression);
            expression.addLeft(lastExpression);
        } else {
            Expression childExpression = lastExpression.getRight();
            childExpression.setParent(expression);
            expression.addLeft(childExpression);
            expression.setParent(lastExpression);
            lastExpression.addRight(expression);
        }
        stack.push(expression);
        currentIndex.incrementAndGet();
    }
}
