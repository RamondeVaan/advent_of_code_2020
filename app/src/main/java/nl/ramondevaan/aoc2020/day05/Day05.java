package nl.ramondevaan.aoc2020.day05;

import java.util.Comparator;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

public class Day05 {
    private final List<Seat> seats;

    public Day05(List<String> lines) {
        SeatParser parser = new SeatParser();
        seats = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        return seats.stream()
                .max(Comparator.comparingInt(Seat::getSeatId))
                .map(Seat::getSeatId)
                .orElseThrow();
    }

    public long solve2() {
        PrimitiveIterator.OfInt seatIdIterator = seats.stream().mapToInt(Seat::getSeatId).sorted().iterator();

        int count = seatIdIterator.nextInt() + 1;

        for (; seatIdIterator.hasNext(); count++) {
            if (seatIdIterator.nextInt() != count) {
                return count;
            }
        }

        throw new IllegalStateException();
    }
}
