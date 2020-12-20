package nl.ramondevaan.aoc2020.day19;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class ConcatenateRule implements Rule {

    public List<Integer> references;

    @Override
    public IntStream references() {
        return references.stream().mapToInt(reference -> reference);
    }

    @Override
    public String toRegex(Map<Integer, String> values) {
        return references.stream().map(values::get).collect(Collectors.joining());
    }
}
