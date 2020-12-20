package nl.ramondevaan.aoc2020.day19;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.stream.Collectors;

public class RegexParser implements Parser<List<String>, String> {

    @Override
    public String parse(List<String> lines) {
        Map<Integer, String> valueLines = new HashMap<>();
        Map<Integer, Rule> referenceLines = new HashMap<>();
        Multimap<Integer, Integer> referencedBy = HashMultimap.create();
        Multimap<Integer, Integer> references = HashMultimap.create();

        for (String line : lines) {
            int colonIndex = line.indexOf(':');
            int index = Integer.parseInt(line.substring(0, colonIndex));
            String value = line.substring(colonIndex + 2);
            if (value.startsWith("\"")) {
                valueLines.put(index, value.substring(1, value.length() - 1));
            } else {
                Rule rule = parseRule(value);
                rule.references().forEach(reference -> {
                    referencedBy.put(reference, index);
                    references.put(index, reference);
                });

                referenceLines.put(index, rule);
            }
        }

        Set<Integer> lastValueIndices = new HashSet<>(valueLines.keySet());
        Set<Integer> nextValueIndices;

        while (!lastValueIndices.contains(0)) {
            nextValueIndices = new HashSet<>();

            for (Integer valueIndex : lastValueIndices) {
                for (Integer referencer : referencedBy.get(valueIndex)) {
                    references.remove(referencer, valueIndex);
                    if (references.get(referencer).size() == 0) {
                        nextValueIndices.add(referencer);

                        Rule rule = referenceLines.get(referencer);
                        String regex = rule.toRegex(valueLines);
                        valueLines.put(referencer, regex);
                    }
                }
            }

            lastValueIndices = nextValueIndices;
        }

        return valueLines.get(0);
    }

    private Rule parseRule(String value) {
        int barIndex = value.indexOf('|');

        if (barIndex >= 0) {
            return parseOrRule(value, barIndex);
        }

        return parseConcatenateRule(value);
    }

    private OrRule parseOrRule(String value, int barIndex) {
        List<Integer> leftReferences = parseIntegers(value.substring(0, barIndex));
        List<Integer> rightReferences = parseIntegers(value.substring(barIndex + 1));

        return new OrRule(leftReferences, rightReferences);
    }

    private ConcatenateRule parseConcatenateRule(String value) {
        return new ConcatenateRule(parseIntegers(value));
    }

    private List<Integer> parseIntegers(String toParse) {
        return Arrays.stream(toParse.trim().split("\\s+")).map(Integer::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }
}
