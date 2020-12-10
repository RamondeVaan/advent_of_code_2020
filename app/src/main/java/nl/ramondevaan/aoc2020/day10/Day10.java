package nl.ramondevaan.aoc2020.day10;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

    private static final int RANGE = 3;
    private final List<Integer> joltages;

    public Day10(List<String> lines) {
        List<Integer> joltagesTemp = lines.stream().map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
        int max = joltagesTemp.stream().max(Comparator.naturalOrder()).orElseThrow();
        joltagesTemp.add(0);
        joltagesTemp.add(max + RANGE);
        joltagesTemp.sort(Comparator.naturalOrder());

        this.joltages = List.copyOf(joltagesTemp);
    }

    public long solve1() {
        Map<Integer, Long> differenceCount = IntStream.range(0, joltages.size() - 1)
                .map(index -> joltages.get(index + 1) - joltages.get(index))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long oneDifferenceCount = differenceCount.get(1);
        long threeDifferenceCount = differenceCount.get(3);

        return oneDifferenceCount * threeDifferenceCount;
    }

    public long solve2() {
        Map<Integer, Long> indexToWays = new HashMap<>();

        indexToWays.put(0, 1L);

        for(int index = 0; index < joltages.size(); index++) {
            int joltage = joltages.get(index);
            long ways = indexToWays.get(index);

            for(int nextIndex = index + 1; nextIndex < joltages.size(); nextIndex++) {
                int nextJoltage = joltages.get(nextIndex);

                if((nextJoltage - joltage) > RANGE) {
                    break;
                }

                indexToWays.merge(nextIndex, ways, Long::sum);
            }
        }

        return indexToWays.get(joltages.size() - 1);
    }
}
