package nl.ramondevaan.aoc2020.day19.BRule;

import java.util.stream.IntStream;

public interface BRule {
    IntStream take(String toValidate, int from);
}
