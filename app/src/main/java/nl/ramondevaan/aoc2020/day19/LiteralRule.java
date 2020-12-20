package nl.ramondevaan.aoc2020.day19;

import lombok.Value;

import java.util.stream.IntStream;

@Value
public class LiteralRule implements Rule {

    String literal;

    @Override
    public IntStream take(String toValidate, int from) {
        if (toValidate.substring(from).startsWith(literal)) {
            return IntStream.of(from + literal.length());
        }

        return IntStream.empty();
    }
}
