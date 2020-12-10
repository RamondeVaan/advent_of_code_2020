package nl.ramondevaan.aoc2020.day09;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.apache.commons.math3.util.CombinatoricsUtils.combinationsIterator;

public class Day09 {

    private final int preambleSize;
    private final List<Long> numbers;

    public Day09(List<String> lines) {
        preambleSize = 25;
        this.numbers = lines.stream().map(Long::parseLong).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        return IntStream.range(preambleSize, numbers.size())
                .filter(index -> !isValid(index))
                .mapToLong(numbers::get)
                .findFirst().orElseThrow();
    }

    private boolean isValid(int index) {
        long value = numbers.get(index);
        List<Long> previousNumbers = numbers.subList(index - preambleSize, index);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(combinationsIterator(preambleSize, 2),
                Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.DISTINCT),
                false)
                .anyMatch(tuple -> Arrays.stream(tuple).mapToLong(previousNumbers::get).sum() == value);
    }

    public long solve2() {
        long target = solve1();

        int start = 0;
        int end = 1;
        LongSummaryStatistics statistics;

        while((statistics = IntStream.rangeClosed(start, end)
                .mapToLong(numbers::get)
                .summaryStatistics()).getSum() != target) {
            if(statistics.getSum() < target) {
                end++;
            } else {
                start++;
            }
        }

        return statistics.getMin() + statistics.getMax();
    }
}
