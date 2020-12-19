package nl.ramondevaan.aoc2020.day18;

import lombok.Value;

import java.util.List;

@Value
public class ExpressionEvaluator {

    private static final String DIGIT_REGEX = "\\d+";

    List<StringFunction> functions;

    public long evaluate(String expression) {
        String current = expression;
        String next;

        while (!current.matches(DIGIT_REGEX)) {
            for (StringFunction function : functions) {
                if (!(next = function.apply(current)).equals(current)) {
                    current = next;
                    break;
                }
            }
        }

        return Long.parseLong(current);
    }
}
