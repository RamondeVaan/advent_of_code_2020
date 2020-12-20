package nl.ramondevaan.aoc2020.day19;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class OrRule implements Rule {
    public List<Integer> leftReferences;
    public List<Integer> rightReferences;

    @Override
    public IntStream references() {
        return IntStream.concat(leftReferences.stream().mapToInt(reference -> reference),
                rightReferences.stream().mapToInt(reference -> reference));
    }

    @Override
    public String toRegex(Map<Integer, String> values) {
        String leftString = leftReferences.stream().map(values::get).collect(Collectors.joining());
        String rightString = rightReferences.stream().map(values::get).collect(Collectors.joining());

        return String.format("(?:%s|%s)", leftString, rightString);
    }
}
