package nl.ramondevaan.aoc2020.day19;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.stream.Collectors;

public class RuleParser implements Parser<List<String>, Rule> {
    @Override
    public Rule parse(List<String> lines) {
        Map<Integer, Rule> valueLines = new HashMap<>();
        Map<Integer, RuleBuilder> referenceLines = new HashMap<>();
        Multimap<Integer, Integer> referencedBy = HashMultimap.create();
        Multimap<Integer, Integer> references = HashMultimap.create();

        for (String line : lines) {
            int colonIndex = line.indexOf(':');
            int index = Integer.parseInt(line.substring(0, colonIndex));
            String value = line.substring(colonIndex + 2);
            if (value.startsWith("\"")) {
                valueLines.put(index, new LiteralRule(value.substring(1, value.length() - 1)));
            } else {
                RuleBuilder rule = parseRule(index, value);
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

                        RuleBuilder builder = referenceLines.get(referencer);
                        Rule rule = builder.build(valueLines);
                        valueLines.put(referencer, rule);
                    }
                }
            }

            lastValueIndices = nextValueIndices;
        }

        return valueLines.get(0);
    }

    private RuleBuilder parseRule(int index, String value) {
        int barIndex = value.indexOf('|');

        if (barIndex >= 0) {
            return parseBarRule(index, value, barIndex);
        }

        return parseSequenceRule(value);
    }

    private RuleBuilder parseBarRule(int index, String value, int barIndex) {
        List<Integer> leftReferences = parseIntegers(value.substring(0, barIndex));
        List<Integer> rightReferences = parseIntegers(value.substring(barIndex + 1));

        int rightIndex = rightReferences.indexOf(index);
        if (rightIndex >= 0) {
            return parseRecursiveRule(index, rightIndex, leftReferences, rightReferences);
        }

        return parseOptionRule(leftReferences, rightReferences);
    }

    private RecursiveRule.RecursiveRuleBuilder parseRecursiveRule(int index, int rightIndex,
                                                                  List<Integer> leftReferences,
                                                                  List<Integer> rightReferences) {
        if (leftReferences.contains(index)) {
            throw new IllegalStateException();
        }
        List<Integer> leftOfIndexReferences = rightReferences.subList(0, rightIndex);
        List<Integer> rightOfIndexReferences = rightReferences.subList(rightIndex + 1, rightReferences.size());

        if (!leftReferences.subList(0, leftOfIndexReferences.size()).equals(leftOfIndexReferences)) {
            throw new IllegalStateException();
        }
        if (!leftReferences.subList(leftReferences.size() - rightOfIndexReferences.size(), leftReferences.size())
                .equals(rightOfIndexReferences)) {
            throw new IllegalStateException();
        }

        return new RecursiveRule.RecursiveRuleBuilder(leftOfIndexReferences, rightOfIndexReferences);
    }

    private OptionRule.OptionRuleBuilder parseOptionRule(List<Integer> leftReferences,
                                                         List<Integer> rightReferences) {
        return new OptionRule.OptionRuleBuilder(leftReferences, rightReferences);
    }

    private SequenceRule.SequenceRuleBuilder parseSequenceRule(String value) {
        return new SequenceRule.SequenceRuleBuilder(parseIntegers(value));
    }

    private List<Integer> parseIntegers(String toParse) {
        return Arrays.stream(toParse.trim().split("\\s+")).map(Integer::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }
}
