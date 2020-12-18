package nl.ramondevaan.aoc2020.day18.token;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenParser implements Parser<String, List<Token>> {

    int nextIndex;
    Character current;
    char[] chars;

    @Override
    public List<Token> parse(String toParse) {
        chars = toParse.trim().toCharArray();
        nextIndex = 0;
        List<Token> tokens = new ArrayList<>();
        current = next();

        while (current != null) {
            if (current == ' ') {
                next();
                continue;
            }
            tokens.add(parseNext());
        }

        return Collections.unmodifiableList(tokens);
    }

    private Token parseNext() {
        if (Character.isDigit(current)) {
            return parseNumber();
        }
        if (current == '+') {
            next();
            return new AddToken();
        }
        if (current == '*') {
            next();
            return new MultiplyToken();
        }
        if (current == '(') {
            next();
            return new ParenthesesOpenToken();
        }
        if (current == ')') {
            next();
            return new ParenthesesCloseToken();
        }

        throw new IllegalArgumentException();
    }

    public Token parseNumber() {
        StringBuilder number = new StringBuilder();
        number.append(current);

        next();
        while (current != null && Character.isDigit(current)) {
            number.append(current);
            next();
        }

        return new NumberToken(Long.parseLong(number.toString()));
    }

    private Character next() {
        if (nextIndex < chars.length) {
            return current = chars[nextIndex++];
        }
        nextIndex++;
        return current = null;
    }
}
