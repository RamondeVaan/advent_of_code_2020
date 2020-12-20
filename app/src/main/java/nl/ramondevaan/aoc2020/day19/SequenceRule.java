package nl.ramondevaan.aoc2020.day19;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class SequenceRule implements Rule {

    List<Rule> rules;

    @Override
    public IntStream take(String toValidate, int from) {
        IntStream ret = IntStream.of(from);

        for (Rule rule : rules) {
            ret = ret.flatMap(nextFrom -> rule.take(toValidate, nextFrom));
        }

        return ret;
    }

    @Value
    public static class SequenceRuleBuilder implements RuleBuilder {

        List<Integer> indices;

        @Override
        public IntStream references() {
            return indices.stream().mapToInt(index -> index);
        }

        @Override
        public Rule build(Map<Integer, Rule> references) {
            List<Rule> rules = indices.stream().map(references::get).collect(Collectors.toUnmodifiableList());
            return new SequenceRule(rules);
        }
    }
}
