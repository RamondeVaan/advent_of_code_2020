package nl.ramondevaan.aoc2020.day15;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 {

    private final List<Integer> numbers;

    public Day15(List<String> lines) {
        numbers = Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public long solve1() {
        return solve(2020);
    }

    public long solve2() {
        return solve(30000000);
    }

    public long solve(int lastNumberIndex) {
        Map<Integer, Deque<Integer>> numberToIndicesMap = new HashMap<>();
        int nextNumber = 0;
        int numberIndex = 0;
        Deque<Integer> previousNumberIndices = new ArrayDeque<>();

        for (Integer number : numbers) {
            previousNumberIndices = add(numberToIndicesMap, number, numberIndex++);
        }

        for (; numberIndex < lastNumberIndex; numberIndex++) {
            nextNumber = previousNumberIndices.size() == 1 ? 0 :
                    previousNumberIndices.getLast() - previousNumberIndices.removeFirst();
            previousNumberIndices = add(numberToIndicesMap, nextNumber, numberIndex);
        }

        return nextNumber;
    }

    private static Deque<Integer> add(Map<Integer, Deque<Integer>> numberToIndicesMap, int value, int index) {
        Deque<Integer> lastIndices = numberToIndicesMap.getOrDefault(value, new ArrayDeque<>(2));
        lastIndices.offer(index);
        numberToIndicesMap.put(value, lastIndices);
        return lastIndices;
    }
}
