package nl.ramondevaan.aoc2020.day17;

import java.util.stream.IntStream;

public class EmptyRange implements Range {

    @Override
    public IntStream stream() {
        return IntStream.empty();
    }

    @Override
    public Range stretch(int... values) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int value : values) {
            min = Math.min(value, min);
            max = Math.max(value, max);
        }

        return RangeImpl.of(min, max);
    }
}
