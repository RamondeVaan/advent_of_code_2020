package nl.ramondevaan.aoc2020.day18;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayDeque;

public class ExpressionParser implements Parser<String, Expression> {

    private ArrayDeque<Expression> expressions;
    private char[] chars;
    private char current;
    private int next;

    @Override
    public Expression parse(String toParse) {
        expressions = new ArrayDeque<>();
        next = 0;
        chars = toParse.toCharArray();
        current = getNext();

        while (next < chars.length) {
            expressions.add(parseNext());
        }

        return expressions.removeFirst();
    }

    public Expression parseNext() {
        while (Character.isSpaceChar(current)) {
            getNext();
        }
        if (Character.isDigit(current)) {
            return parseDigit();
        }
        if (current == '+') {
            return parseAdd();
        }
        if (current == '*') {
            return parseMultiply();
        }
        if (current == '(') {
            return parseParentheses();
        }

        throw new IllegalStateException();
    }

    public Expression parseDigit() {
        StringBuilder digit = new StringBuilder();
        digit.append(current);

        while (hasNext() && Character.isDigit(getNext())) {
            digit.append(current);
        }

        return new NumberExpression(Long.parseLong(digit.toString()));
    }

    public Expression parseAdd() {
        Expression left = expressions.removeLast();
        getNext();
        Expression right = parseNext();

        return new AddExpression(left, right);
    }

    public Expression parseMultiply() {
        Expression left = expressions.removeLast();
        getNext();
        Expression right = parseNext();

        return new MultiplyExpression(left, right);
    }

    public Expression parseParentheses() {
        while (current != ')') {
            getNext();
            expressions.add(parseNext());
        }
        if (hasNext()) {
            getNext();
        }

        return new ParenthesesExpression(expressions.removeLast());
    }

    private boolean hasNext() {
        return next < chars.length;
    }

    private char getNext() {
        return current = chars[next++];
    }
}
