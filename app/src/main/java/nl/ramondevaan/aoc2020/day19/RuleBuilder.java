package nl.ramondevaan.aoc2020.day19;

import java.util.Map;
import java.util.stream.IntStream;

public interface RuleBuilder {
    IntStream references();

    Rule build(Map<Integer, Rule> references);
}
