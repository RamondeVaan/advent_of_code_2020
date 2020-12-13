package nl.ramondevaan.aoc2020.day06;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day06 {

    private final List<List<String>> answersByGroup;

    public Day06(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        this.answersByGroup = partitioner.partition(lines);
    }

    public long solve1() {
        return answersByGroup.stream()
                .mapToLong(answers -> flatMapChars(answers)
                        .distinct()
                        .count())
                .sum();
    }

    public long solve2() {
        return answersByGroup.stream()
                .mapToLong(answers -> numberOfValuesEqualTo(
                        flatMapChars(answers)
                                .boxed()
                                .collect(Collectors.groupingBy(
                                        Function.identity(),
                                        Collectors.counting())),
                        (long) answers.size()))
                .sum();
    }

    private static IntStream flatMapChars(List<String> lines) {
        return lines.stream().flatMapToInt(String::chars);
    }

    private static long numberOfValuesEqualTo(Map<?, Long> map, Long compareTo) {
        return map.values().stream().filter(value -> Objects.equals(value, compareTo)).count();
    }
}
