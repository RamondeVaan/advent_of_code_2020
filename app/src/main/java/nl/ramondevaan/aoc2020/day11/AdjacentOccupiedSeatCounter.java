package nl.ramondevaan.aoc2020.day11;

import java.util.Arrays;

public class AdjacentOccupiedSeatCounter implements SeatCounter {
    @Override
    public long count(Seats seats, int x, int y) {
        return Arrays.stream(Direction.values())
                .map(direction -> Coordinate.of(x + direction.x, y + direction.y))
                .filter(seats::isInRange)
                .map(seats::getPositionState)
                .filter(position -> position.equals(PositionState.OCCUPIED))
                .count();
    }
}
