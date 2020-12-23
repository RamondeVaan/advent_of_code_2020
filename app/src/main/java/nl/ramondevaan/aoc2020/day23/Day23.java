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

        StringBuilder stringBuilder = new StringBuilder();
        Cups.Cup one = cups.one;
        Cups.Cup next = one.next;

        while (next.label != one.label) {
            stringBuilder.append(next.label);
            next = next.next;
        }

        return stringBuilder.toString();
    }

    public long solve2() {
        Cups cups = new Cups(initialCups, 1000000);

        for (int i = 0; i < 10000000; i++) {
            cups.playRound();
        }

        Cups.Cup oneNext = cups.one.next;
        long oneNextLabel = oneNext.label;
        long oneNextNextLabel = oneNext.next.label;

        return oneNextLabel * oneNextNextLabel;

//        Deque<Integer> deque = solve(initialCups, 1000000, 10000000);
//
//        int one;
//
//        do {
//            one = deque.removeFirst();
//            deque.addLast(one);
//        } while(one != 1);
//
//        long oneAfterOne = deque.removeFirst();
//        long twoAfterOne = deque.removeFirst();
//
//        return oneAfterOne * twoAfterOne;
    }
}
