package nl.ramondevaan.aoc2020.day18;

import java.util.regex.Pattern;

public class AddStringFunction implements StringFunction {

    private static final String ADD_REGEX = "(\\d+)\\s?\\+\\s?(\\d+)";
    private static final Pattern ADD_PATTERN = Pattern.compile(ADD_REGEX);

    @Override
    public String apply(String input) {
        return ADD_PATTERN.matcher(input).replaceFirst((result) -> {
            long left = Long.parseLong(result.group(1));
            long right = Long.parseLong(result.group(2));
            return String.valueOf(left + right);
        });
    }
}
