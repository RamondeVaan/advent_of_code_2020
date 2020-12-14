package nl.ramondevaan.aoc2020.day14;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationParser implements Parser<String, Operation> {

    private static final String MASK_REGEX = "mask = ([X01]{36})";
    private static final String MEMORY_REGEX = "mem\\[(\\d+)] = (\\d+)";
    private static final Pattern MASK_PATTERN = Pattern.compile(MASK_REGEX);
    private static final Pattern MEMORY_PATTERN = Pattern.compile(MEMORY_REGEX);

    @Override
    public Operation parse(String toParse) {
        Matcher maskMatcher = MASK_PATTERN.matcher(toParse);

        if (maskMatcher.matches()) {
            return parseMaskOperation(maskMatcher);
        }

        Matcher memoryMatcher = MEMORY_PATTERN.matcher(toParse);

        if (memoryMatcher.matches()) {
            return parseMemoryOperation(memoryMatcher);
        }

        throw new IllegalStateException();
    }

    private Operation parseMaskOperation(Matcher matcher) {
        String mask = matcher.group(1);

        String withZeros = mask.replace('X', '0');
        String withOnes = mask.replace('X', '1');

        long maskWithZeros = Long.parseLong(withZeros, 2);
        long maskWithOnes = Long.parseLong(withOnes, 2);

        return new MaskOperation(maskWithZeros, maskWithOnes);
    }

    private Operation parseMemoryOperation(Matcher matcher) {
        long memoryAddress = Long.parseLong(matcher.group(1));
        long value = Long.parseLong(matcher.group(2));

        return new MemoryOperation(memoryAddress, value);
    }
}
