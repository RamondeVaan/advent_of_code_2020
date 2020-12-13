package nl.ramondevaan.aoc2020.day09;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.apache.commons.math3.util.CombinatoricsUtils.combinationsIterator;

public class Day09 {

    private static final int PREAMBLE_SIZE = 25;
    private final List<Long> numbers;

    public Day09(List<String> lines) {
        this.numbers = lines.stream().map(Long::parseLong).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        return IntStream.range(PREAMBLE_SIZE, numbers.size())
                .filter(index -> !isValid(index))
                .mapToLong(numbers::get)
                .findFirst().orElseThrow();
    }

    private boolean isValid(int index) {
        long value = numbers.get(index);
        List<Long> previousNumbers = numbers.subList(index - PREAMBLE_SIZE, index);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(combinationsIterator(PREAMBLE_SIZE, 2),
                Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.DISTINCT),
                false)
                .anyMatch(tuple -> Arrays.stream(tuple).mapToLong(previousNumbers::get).sum() == value);
    }

    public long solve2() {
        long target = solve1();

        int start = 0;
        int end = 1;
        LongSummaryStatistics statistics;

        while ((statistics = IntStream.rangeClosed(start, end)
                .mapToLong(numbers::get)
                .summaryStatistics()).getSum() != target) {
            if (statistics.getSum() < target) {
                end++;
            } else {
                start++;
            }
        }

        return statistics.getMin() + statistics.getMax();
    }
}
