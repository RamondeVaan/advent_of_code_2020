package nl.ramondevaan.aoc2020.day18;

import lombok.Value;

import java.util.List;
import java.util.regex.Pattern;

@Value
public class ParenthesesStringFunction implements StringFunction {

    private static final String PARENTHESES_REGEX = "\\(([\\d+* ]+)\\)";
    private static final Pattern PARENTHESES_PATTERN = Pattern.compile(PARENTHESES_REGEX);

    List<StringFunction> functions;

    @Override
    public String apply(String input) {
        return PARENTHESES_PATTERN.matcher(input).replaceFirst((result) -> {
            String current = result.group(1);

            for (StringFunction function : functions) {
                String next = function.apply(current);
                if (!next.equals(current)) {
                    return "(" + next + ")";
                }
            }

            return current;
        });
    }
}
