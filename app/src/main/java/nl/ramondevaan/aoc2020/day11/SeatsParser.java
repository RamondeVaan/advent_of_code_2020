package nl.ramondevaan.aoc2020.day11;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatsParser implements Parser<List<String>, Seats> {

    private final static char EMPTY = 'L';
    private final static char FLOOR = '.';
    private final static char OCCUPIED = '#';

    @Override
    public Seats parse(List<String> lines) {
        Map<Coordinate, PositionState> coordinateToPositionStateMap = new HashMap<>(
                lines.size() * lines.get(0).length());

        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                coordinateToPositionStateMap.put(Coordinate.of(x, y), toPositionState(chars[x]));
            }
        }

        return new Seats(coordinateToPositionStateMap);
    }

    private PositionState toPositionState(char character) {
        switch (character) {
            case EMPTY:
                return PositionState.EMPTY;
            case FLOOR:
                return PositionState.FLOOR;
            case OCCUPIED:
                return PositionState.OCCUPIED;
        }

        throw new IllegalArgumentException();
    }
}
