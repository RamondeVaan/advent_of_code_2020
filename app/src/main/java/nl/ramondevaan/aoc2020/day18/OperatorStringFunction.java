package nl.ramondevaan.aoc2020.day18;

import java.util.regex.Pattern;

public class OperatorStringFunction implements StringFunction {

    private static final String OPERATOR_REGEX = "(\\d+)\\s?([+*])\\s?(\\d+)";
    private static final Pattern OPERATOR_PATTERN = Pattern.compile(OPERATOR_REGEX);

    @Override
    public String apply(String input) {
        return OPERATOR_PATTERN.matcher(input).replaceFirst((result) -> {
            long left = Long.parseLong(result.group(1));
            long right = Long.parseLong(result.group(3));
            if (result.group(2).charAt(0) == '+') {
                return String.valueOf(left + right);
            }
            return String.valueOf(left * right);
        });
    }
}
