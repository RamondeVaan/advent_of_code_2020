package nl.ramondevaan.aoc2020.day01;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.math3.util.CombinatoricsUtils.combinationsIterator;

@Builder
public class Day01 {

    private final int sum;
    private final List<Long> values;

    public long solve(int tupleSize) {
        return tuples(tupleSize)
                .filter(values -> values.stream().reduce(0L, Long::sum) == sum)
                .findFirst()
                .map(values -> values.stream().reduce(1L, (a, b) -> a * b))
                .orElseThrow();
    }

    public Stream<List<Long>> tuples(int tupleSize) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(combinationsIterator(values.size(), tupleSize),
                Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.DISTINCT),
                false)
                .map(arr -> Arrays.stream(arr).mapToObj(values::get).collect(Collectors.toList()));
    }
}
