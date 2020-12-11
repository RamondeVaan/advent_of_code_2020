package nl.ramondevaan.aoc2020.day11;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Objects;

public class Day11 {

    private final Seats initialSeats;

    public Day11(List<String> lines) {
        Parser<List<String>, Seats> parser = new SeatsParser();
        this.initialSeats = parser.parse(lines);
    }

    public long solve1() {
        return solve(new AdjacentOccupiedSeatCounter(), 4L);
    }

    public long solve2() {
        return solve(new DirectionalOccupiedSeatCounter(), 5L);
    }

    private long solve(SeatCounter seatCounter, long maxOccupiedSeats) {
        Seats oldSeats = initialSeats;
        Seats newSeats;

        while (!Objects.equals(newSeats = nextSeats(oldSeats, seatCounter, maxOccupiedSeats), oldSeats)) {
            oldSeats = newSeats;
        }

        return newSeats.seats().stream()
                .filter(positionState -> positionState.equals(PositionState.OCCUPIED))
                .count();
    }

    private Seats nextSeats(Seats seats, SeatCounter seatCounter, long maxOccupiedSeats) {
        PositionState[][] positions = new PositionState[seats.getHeight()][seats.getWidth()];

        for (int y = 0; y < seats.getHeight(); y++) {
            for (int x = 0; x < seats.getWidth(); x++) {
                PositionState positionState = seats.getPositionState(x, y);
                if (positionState.equals(PositionState.FLOOR)) {
                    positions[y][x] = PositionState.FLOOR;
                } else if (positionState.equals(PositionState.EMPTY)) {
                    long occupiedSeats = seatCounter.count(seats, x, y);
                    if (occupiedSeats == 0L) {
                        positions[y][x] = PositionState.OCCUPIED;
                    } else {
                        positions[y][x] = PositionState.EMPTY;
                    }
                } else {
                    long occupiedSeats = seatCounter.count(seats, x, y);
                    if (occupiedSeats >= maxOccupiedSeats) {
                        positions[y][x] = PositionState.EMPTY;
                    } else {
                        positions[y][x] = PositionState.OCCUPIED;
                    }
                }
            }
        }

        return new Seats(positions);
    }

    private void printSeats(Seats seats) {
        for (int y = 0; y < seats.getHeight(); y++) {
            for (int x = 0; x < seats.getWidth(); x++) {
                switch (seats.getPositionState(x, y)) {
                    case OCCUPIED:
                        System.out.print('#');
                        break;
                    case EMPTY:
                        System.out.print('L');
                        break;
                    case FLOOR:
                        System.out.print('.');
                        break;
                }
            }
            System.out.println();
        }
    }
}
