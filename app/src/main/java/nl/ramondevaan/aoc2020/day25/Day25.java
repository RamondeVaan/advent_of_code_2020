package nl.ramondevaan.aoc2020.day25;

import java.util.List;

public class Day25 {

    private final static long DIVISION_VALUE = 20201227;
    private final static int SUBJECT_NUMBER = 7;

    private final long doorPublicKey;
    private final long cardPublicKey;

    public Day25(List<String> lines) {
        cardPublicKey = Long.parseLong(lines.get(0));
        doorPublicKey = Long.parseLong(lines.get(1));
    }

    public long solve() {
        int loopSize = findLoopSize(cardPublicKey);

        return loop(doorPublicKey, loopSize);
    }

    private int findLoopSize(long targetSubjectNumber) {
        long value = 1;
        int count = 0;

        while (value != targetSubjectNumber) {
            value = (value * SUBJECT_NUMBER) % DIVISION_VALUE;
            count++;
        }

        return count;
    }

    private long loop(long subjectNumber, int loopSize) {
        long value = 1;

        for (int i = 0; i < loopSize; i++) {
            value = (value * subjectNumber) % DIVISION_VALUE;
        }

        return value;
    }
}
