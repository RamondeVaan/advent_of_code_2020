package nl.ramondevaan.aoc2020.day19;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Value
public class RecursiveRule implements Rule {

    Rule left;
    Rule right;

    @Override
    public IntStream take(String toValidate, int from) {
        return IntStream.rangeClosed(1, toValidate.length() - from).flatMap(times -> {
            IntStream ret = IntStream.of(from);

            for (int i = 0; i < times; i++) {
                ret = ret.flatMap(nextFrom -> left.take(toValidate, nextFrom));
            }
            for (int i = 0; i < times; i++) {
                ret = ret.flatMap(nextFrom -> right.take(toValidate, nextFrom));
            }

            return ret;
        });
    }

    @Value
    public static class RecursiveRuleBuilder implements RuleBuilder {

        List<Integer> leftOfIndexReferences;
        List<Integer> rightOfIndexReferences;

        @Override
        public IntStream references() {
            return IntStream.concat(leftOfIndexReferences.stream().mapToInt(reference -> reference),
                    rightOfIndexReferences.stream().mapToInt(reference -> reference));
        }

        @Override
        public Rule build(Map<Integer, Rule> references) {
            Rule left = buildRule(leftOfIndexReferences, references);
            Rule right = buildRule(rightOfIndexReferences, references);

            return new RecursiveRule(left, right);
        }

        private Rule buildRule(List<Integer> indices, Map<Integer, Rule> references) {
            if (indices.isEmpty()) {
                return (toValidate, from) -> IntStream.of(from);
            } else {
                SequenceRule.SequenceRuleBuilder builder = new SequenceRule.SequenceRuleBuilder(indices);
                return builder.build(references);
            }
        }
    }
}
