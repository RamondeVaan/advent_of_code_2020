package nl.ramondevaan.aoc2020.day23;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Cups {

    private static final int PICK_UP = 3;
    private static final int PICK_UP_MINUS_ONE = PICK_UP - 1;

    private Cup current;
    private final Cup one;

    public Cups(List<Integer> cupLabels, int size) {
        Map<Integer, Cup> map = new HashMap<>();

        Cup cup = null;
        if (cupLabels.size() > 0) {
            cup = new Cup(null, null, cupLabels.get(0));
            map.put(cup.label, cup);
            current = cup;
            for (int index = 1; index < cupLabels.size(); index++) {
                int label = cupLabels.get(index);
                Cup next = new Cup(null, null, label);
                cup.next = next;
                map.put(label, next);
                cup = next;
            }
            for (Cup c : map.values()) {
                c.minusOne = map.get(c.label - 1);
            }
        }

        if (size > cupLabels.size()) {
            Cup minusOne = map.get(cupLabels.size());
            Cup next = new Cup(null, minusOne, cupLabels.size() + 1);
            if (cup != null) {
                cup.next = next;
            } else {
                current = next;
            }
            cup = next;
            map.put(cup.label, cup);
            for (int i = cupLabels.size() + 2; i <= size; i++) {
                next = new Cup(null, cup, i);
                cup.next = next;
                cup = next;
            }
        }

        if (cup == null) {
            throw new IllegalStateException();
        }

        cup.next = current;

        Cup max = map.get(cupLabels.size());
        max = cup.label > max.label ? cup : max;

        one = map.get(1);
        one.minusOne = max;
    }

    public IntStream fromOne() {
        return Stream.iterate(one.next, cup -> cup != one, cup -> cup.next)
                .mapToInt(cup -> cup.label);
    }

    public void playRound() {
        Cup[] pickedUp = pickUp();

        Cup destination = current.minusOne;

        while (contains(pickedUp, destination)) {
            destination = destination.minusOne;
        }

        connect(pickedUp[PICK_UP_MINUS_ONE], destination.next);
        connect(destination, pickedUp[0]);

        current = current.next;
    }

    private Cup[] pickUp() {
        Cup[] cups = new Cup[PICK_UP];
        Cup last = current;
        for (int i = 0; i < cups.length; i++) {
            last = last.next;
            cups[i] = last;
        }
        connect(current, last.next);

        return cups;
    }

    private boolean contains(Cup[] cups, Cup cup) {
        for (Cup value : cups) {
            if (value.label == cup.label) {
                return true;
            }
        }

        return false;
    }

    private void connect(Cup previous, Cup next) {
        previous.next = next;
    }

    @EqualsAndHashCode(of = "label")
    @AllArgsConstructor
    public static class Cup {
        Cup next;
        Cup minusOne;
        int label;
    }
}
