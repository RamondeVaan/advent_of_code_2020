package nl.ramondevaan.aoc2020.day05;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Day05 {
    private final List<Seat> seats;

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

    public static Day05Builder builder() {
        return new Day05Builder();
    }

    public static class Day05Builder {
        private final SeatParser seatParser;
        private List<String> lines;

        Day05Builder() {
            seatParser = new SeatParser();
        }

        public Day05Builder lines(List<String> lines) {
            this.lines = lines;
            return this;
        }

        private List<Seat> parseSeats() {
            return lines.stream().map(seatParser::parse).collect(Collectors.toList());
        }

        public Day05 build() {
            return new Day05(parseSeats());
        }

        public String toString() {
            return "Day05.Day05Builder(seats=" + this.lines + ")";
        }
    }
}
