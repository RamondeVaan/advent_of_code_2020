package nl.ramondevaan.aoc2020.day23;

import java.util.List;
import java.util.stream.Collectors;

public class Day23 {

    List<Integer> initialCups;

    public Day23(List<String> lines) {
        initialCups = lines.get(0).chars().map(value -> value - 48).boxed().collect(Collectors.toUnmodifiableList());
    }

    public String solve1() {
        Cups cups = new Cups(initialCups, initialCups.size());

        for (int i = 0; i < 100; i++) {
            cups.playRound();
        }

        return cups.fromOne()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    public long solve2() {
        Cups cups = new Cups(initialCups, 1000000);

        for (int i = 0; i < 10000000; i++) {
            cups.playRound();
        }

        return cups.fromOne()
                .mapToLong(Long::valueOf).limit(2)
                .reduce(1L, (left, right) -> left * right);
    }
}
