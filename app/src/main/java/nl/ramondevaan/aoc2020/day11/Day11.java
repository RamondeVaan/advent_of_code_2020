package nl.ramondevaan.aoc2020.day11;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {

    private final Seats initialSeats;

    public Day11(List<String> lines) {
        Parser<List<String>, Seats> parser = new SeatsParser();
        this.initialSeats = parser.parse(lines);
    }

    public long solve1() {
        return solve(getNextPositionStateMapperMap(new AdjacentOccupiedSeatCounter(), 4L));
    }

    public long solve2() {
        return solve(getNextPositionStateMapperMap(new DirectionalOccupiedSeatCounter(), 5L));
    }

    private long solve(Map<PositionState, PositionStateMapper> nextPositionStateMapperMap) {
        Seats oldSeats = initialSeats;
        Seats newSeats;

        while (!Objects.equals(newSeats = nextSeats(oldSeats, nextPositionStateMapperMap), oldSeats)) {
            oldSeats = newSeats;
        }

        return newSeats.seats().stream()
                .filter(positionState -> positionState.equals(PositionState.OCCUPIED))
                .count();
    }

    private Map<PositionState, PositionStateMapper> getNextPositionStateMapperMap(SeatCounter seatCounter,
                                                                                  long maxOccupiedSeats) {
        return Map.of(
                PositionState.FLOOR, (seats, coordinate) -> PositionState.FLOOR,
                PositionState.EMPTY, (seats, coordinate) -> seatCounter.count(seats, coordinate) == 0L
                        ? PositionState.OCCUPIED : PositionState.EMPTY,
                PositionState.OCCUPIED, (seats, coordinate) -> seatCounter.count(seats, coordinate) >= maxOccupiedSeats
                        ? PositionState.EMPTY : PositionState.OCCUPIED
        );
    }

    private Seats nextSeats(Seats seats, Map<PositionState, PositionStateMapper> nextPositionStateMapperMap) {
        Map<Coordinate, PositionState> map = seats.coordinates().stream().collect(
                Collectors.toMap(Function.identity(),
                        coordinate -> nextPositionStateMapperMap.get(seats.getPositionState(coordinate))
                                .map(seats, coordinate)));

        return new Seats(map);
    }
}
