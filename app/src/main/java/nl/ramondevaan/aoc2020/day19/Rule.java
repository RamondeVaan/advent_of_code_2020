package nl.ramondevaan.aoc2020.day19;

import java.util.Map;
import java.util.stream.IntStream;

public interface Rule {
    IntStream references();

    String toRegex(Map<Integer, String> values);
}
