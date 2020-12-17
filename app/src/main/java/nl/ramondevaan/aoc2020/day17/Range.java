package nl.ramondevaan.aoc2020.day17;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public interface Range {
    default PrimitiveIterator.OfInt iterator() {
        return stream().iterator();
    }

    Range stretch(int... values);

    IntStream stream();
}
