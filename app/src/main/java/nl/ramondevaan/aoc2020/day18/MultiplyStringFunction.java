package nl.ramondevaan.aoc2020.day18;

import java.util.regex.Pattern;

public class MultiplyStringFunction implements StringFunction {

    private static final String MULTIPLY_REGEX = "(\\d+)\\s?\\*\\s?(\\d+)";
    private static final Pattern MULTIPLY_PATTERN = Pattern.compile(MULTIPLY_REGEX);

    @Override
    public String apply(String input) {
        return MULTIPLY_PATTERN.matcher(input).replaceFirst((result) -> {
            long left = Long.parseLong(result.group(1));
            long right = Long.parseLong(result.group(2));
            return String.valueOf(left * right);
        });
    }
}
