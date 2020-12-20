package nl.ramondevaan.aoc2020.day19.BRule;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Value
public class OptionRule implements BRule {

    BRule left;
    BRule right;

    @Override
    public IntStream take(String toValidate, int from) {
        return IntStream.concat(
                left.take(toValidate, from),
                right.take(toValidate, from)
        );
    }

    @Value
    public static class OptionRuleBuilder implements BRuleBuilder {

        List<Integer> leftReferences;
        List<Integer> rightReferences;

        @Override
        public IntStream references() {
            return IntStream.concat(leftReferences.stream().mapToInt(index -> index),
                    rightReferences.stream().mapToInt(index -> index));
        }

        @Override
        public BRule build(Map<Integer, BRule> references) {
            SequenceRule.SequenceRuleBuilder leftBuilder = new SequenceRule.SequenceRuleBuilder(leftReferences);
            SequenceRule.SequenceRuleBuilder rightBuilder = new SequenceRule.SequenceRuleBuilder(rightReferences);

            return new OptionRule(leftBuilder.build(references), rightBuilder.build(references));
        }
    }
}
