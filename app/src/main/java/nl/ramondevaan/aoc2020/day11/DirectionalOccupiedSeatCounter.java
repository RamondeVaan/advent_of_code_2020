package nl.ramondevaan.aoc2020.day11;

import java.util.Arrays;
import java.util.stream.Stream;

public class DirectionalOccupiedSeatCounter implements SeatCounter {
    @Override
    public long count(Seats seats, Coordinate coordinate) {
        return Arrays.stream(Direction.values())
                .mapToLong(direction -> hasDirectionalOccupied(seats, coordinate, direction) ? 1L : 0L)
                .sum();
    }

    private boolean hasDirectionalOccupied(Seats seats, Coordinate coordinate, Direction direction) {
        return Stream.iterate(coordinate.add(direction.x, direction.y),
                seats::isInRange,
                nextCoordinate -> nextCoordinate.add(direction.x, direction.y))
                .map(seats::getPositionState)
                .filter(positionState -> positionState.equals(PositionState.EMPTY) ||
                        positionState.equals(PositionState.OCCUPIED))
                .findFirst()
                .filter(positionState -> positionState.equals(PositionState.OCCUPIED))
                .isPresent();
    }
}
