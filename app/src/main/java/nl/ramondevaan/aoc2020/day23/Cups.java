package nl.ramondevaan.aoc2020.day23;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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

        ListIterator<Integer> iterator = cupLabels.listIterator(cupLabels.size());
        Cup max;
        if (size > cupLabels.size()) {
            max = new Cup(null, null, size);
        } else {
            int label = iterator.previous();
            max = new Cup(null, null, label);
            map.put(label, max);
        }
        Cup cup = max;
        Cup next;

        for (int label = size - 1; label > cupLabels.size(); label--) {
            next = new Cup(cup, null, label);
            cup.minusOne = next;
            cup = next;
        }
        while (iterator.hasPrevious()) {
            int label = iterator.previous();
            next = new Cup(cup, null, label);
            map.put(label, next);
            cup = next;
        }
        for (Cup c : map.values()) {
            c.minusOne = map.get(c.label - 1);
        }

        current = cup;
        max.next = cup;

        if (cupLabels.isEmpty()) {
            one = cup;
            one.minusOne = max;
        } else {
            one = map.get(1);
            if (size > cupLabels.size()) {
                one.minusOne = max;
                map.get(cupLabels.get(cupLabels.size() - 1)).next.minusOne = map.get(cupLabels.size());
            } else {
                one.minusOne = map.get(cupLabels.size());
            }
        }
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
