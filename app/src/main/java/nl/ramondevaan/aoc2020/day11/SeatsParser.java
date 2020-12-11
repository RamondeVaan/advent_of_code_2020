package nl.ramondevaan.aoc2020.day11;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;

public class SeatsParser implements Parser<List<String>, Seats> {

    private final static char EMPTY = 'L';
    private final static char FLOOR = '.';
    private final static char OCCUPIED = '#';

    @Override
    public Seats parse(List<String> lines) {
        PositionState[][] positions = lines.stream()
                .map(line -> line.chars().mapToObj(this::toPositionState).toArray(PositionState[]::new))
                .toArray(PositionState[][]::new);
        return new Seats(positions);
    }

    private PositionState toPositionState(int character) {
        switch (character) {
            case EMPTY: return PositionState.EMPTY;
            case FLOOR: return PositionState.FLOOR;
            case OCCUPIED: return PositionState.OCCUPIED;
        }

        throw new IllegalArgumentException();
    }
}
