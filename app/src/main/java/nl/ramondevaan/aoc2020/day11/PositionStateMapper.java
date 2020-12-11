package nl.ramondevaan.aoc2020.day11;

@FunctionalInterface
public interface PositionStateMapper {
    PositionState map(Seats seats, Coordinate coordinate);
}
