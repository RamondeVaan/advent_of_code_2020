package nl.ramondevaan.aoc2020.day18;

import java.util.regex.Pattern;

public class RemoveParenthesesStringFunction implements StringFunction {
    private static final String PARENTHESES_REGEX = "\\((\\d+)\\)";
    private static final Pattern PARENTHESES_PATTERN = Pattern.compile(PARENTHESES_REGEX);

    @Override
    public String apply(String input) {
        return PARENTHESES_PATTERN.matcher(input).replaceFirst((result) -> result.group(1));
    }
}
