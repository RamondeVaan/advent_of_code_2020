package nl.ramondevaan.aoc2020.day11;

import java.util.Arrays;

public class AdjacentOccupiedSeatCounter implements SeatCounter {
    @Override
    public long count(Seats seats, Coordinate coordinate) {
        return Arrays.stream(Direction.values())
                .map(direction -> coordinate.add(direction.x, direction.y))
                .filter(seats::isInRange)
                .map(seats::getPositionState)
                .filter(position -> position.equals(PositionState.OCCUPIED))
                .count();
    }
}
