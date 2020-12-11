package nl.ramondevaan.aoc2020.day11;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class Seats {
    private final PositionState[][] positions;

    public int getWidth() {
        return positions[0].length;
    }

    public int getHeight() {
        return positions.length;
    }

    public PositionState getPositionState(int x, int y) {
        return positions[y][x];
    }

    public PositionState getPositionState(Coordinate coordinate) {
        return positions[coordinate.y][coordinate.x];
    }

    public List<PositionState> seats() {
        List<PositionState> ret = new ArrayList<>();

        for (int y = 0; y < getHeight(); y++) {
            ret.addAll(Arrays.asList(positions[y]));
        }

        return ret;
    }

    public boolean isInRange(int x, int y) {
        return 0 <= x && x < getWidth() && 0 <= y && y < getHeight();
    }

    public boolean isInRange(Coordinate coordinate) {
        return 0 <= coordinate.x && coordinate.x < getWidth() &&
                0 <= coordinate.y && coordinate.y < getHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seats seats = (Seats) o;
        return Arrays.deepEquals(positions, seats.positions);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(positions);
    }
}
