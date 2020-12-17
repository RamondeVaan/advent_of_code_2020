package nl.ramondevaan.aoc2020.day17;

import lombok.RequiredArgsConstructor;

import java.util.stream.IntStream;

@RequiredArgsConstructor(staticName = "of")
public class RangeImpl implements Range {

    private final int min;
    private final int max;

    @Override
    public IntStream stream() {
        return IntStream.rangeClosed(min, max);
    }

    @Override
    public Range stretch(int... values) {
        int min = this.min;
        int max = this.max;

        for (int value : values) {
            min = Math.min(value, min);
            max = Math.max(value, max);
        }

        return new RangeImpl(min, max);
    }
}
