package nl.ramondevaan.aoc2020.day11;

import java.util.Arrays;
import java.util.stream.Stream;

public class DirectionalOccupiedSeatCounter implements SeatCounter {
    @Override
    public long count(Seats seats, int x, int y) {
        return Arrays.stream(Direction.values())
                .mapToLong(direction -> hasDirectionalOccupied(seats, x, y, direction) ? 1L : 0L)
                .sum();
    }

    private boolean hasDirectionalOccupied(Seats seats, int x, int y, Direction direction) {
        return Stream.iterate(Coordinate.of(x + direction.x, y + direction.y),
                seats::isInRange,
                pair -> Coordinate.of(pair.x + direction.x, pair.y + direction.y))
                .map(seats::getPositionState)
                .filter(positionState -> positionState.equals(PositionState.EMPTY) ||
                        positionState.equals(PositionState.OCCUPIED))
                .findFirst()
                .filter(positionState -> positionState.equals(PositionState.OCCUPIED))
                .isPresent();
    }
}
