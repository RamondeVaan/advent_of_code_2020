package nl.ramondevaan.aoc2020.day23;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cups {

    private static final int PICK_UP = 3;
    private static final int PICK_UP_MINUS_ONE = PICK_UP - 1;
    private Cup current;
    public final Cup one;

    public Cups(List<Integer> cupLabels, int size) {
        Map<Integer, Cup> map = new HashMap<>();

        Cup cup = null;
        if (cupLabels.size() > 0) {
            cup = new Cup(null, null, null, cupLabels.get(0));
            map.put(cup.label, cup);
            current = cup;
            for (int index = 1; index < cupLabels.size(); index++) {
                int label = cupLabels.get(index);
                Cup next = new Cup(cup, null, null, label);
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
            cup = new Cup(cup, null, minusOne, cupLabels.size() + 1);
            if (cup.previous != null) {
                cup.previous.next = cup;
            } else {
                current = cup;
            }
            map.put(cup.label, cup);
            for (int i = cupLabels.size() + 2; i <= size; i++) {
                Cup next = new Cup(cup, null, cup, i);
                cup.next = next;
                cup = next;
            }
        }

        current.previous = cup;
        cup.next = current;

        Cup max = map.get(cupLabels.size());
        max = cup.label > max.label ? cup : max;

        one = map.get(1);
        one.minusOne = max;
    }

    public void playRound() {
        Cup[] pickedUp = pickUp();

        Cup destination = current.minusOne;

        while (contains(pickedUp, destination)) {
            destination = destination.minusOne;
        }

        pickedUp[PICK_UP_MINUS_ONE].next = destination.next;
        destination.next.previous = pickedUp[PICK_UP_MINUS_ONE];
        destination.next = pickedUp[0];
        pickedUp[0].previous = destination;

        current = current.next;
    }

    private Cup[] pickUp() {
        Cup[] cups = new Cup[PICK_UP];
        Cup last = current;
        for (int i = 0; i < cups.length; i++) {
            last = last.next;
            cups[i] = last;
        }
        current.next = last.next;
        last.next.previous = current;

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

    @EqualsAndHashCode(of = "label")
    @AllArgsConstructor
    public static class Cup {
        Cup previous;
        Cup next;
        Cup minusOne;
        int label;
    }
}
