package nl.ramondevaan.aoc2020.day19.BRule;

import java.util.Map;
import java.util.stream.IntStream;

public interface BRuleBuilder {
    IntStream references();

    BRule build(Map<Integer, BRule> references);
}
