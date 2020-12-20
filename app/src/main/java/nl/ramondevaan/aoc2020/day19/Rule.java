package nl.ramondevaan.aoc2020.day19;

import java.util.stream.IntStream;

public interface Rule {
    IntStream take(String toValidate, int from);
}
