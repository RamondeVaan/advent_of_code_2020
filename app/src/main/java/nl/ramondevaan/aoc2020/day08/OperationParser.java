package nl.ramondevaan.aoc2020.day08;

import com.google.common.base.CharMatcher;
import nl.ramondevaan.aoc2020.util.Parser;

public class OperationParser implements Parser<String, Operation> {

    @Override
    public Operation parse(String toParse) {
        String[] split = toParse.split("\\s+");

        String type = split[0];
        String argumentString = CharMatcher.is('+').removeFrom(split[1]);
        int argument = Integer.parseInt(argumentString);

        return new Operation(type, argument);
    }
}
