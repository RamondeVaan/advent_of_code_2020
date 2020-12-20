package nl.ramondevaan.aoc2020.day17;

import java.util.stream.IntStream;

public interface Range {

    Range stretch(int... values);

    IntStream stream();
}
