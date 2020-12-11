package nl.ramondevaan.aoc2020.day11;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Seats {
    private final Map<Coordinate, PositionState> coordinateToPositionStateMap;

    public PositionState getPositionState(Coordinate coordinate) {
        return coordinateToPositionStateMap.get(coordinate);
    }

    public Set<Coordinate> coordinates() {
        return coordinateToPositionStateMap.keySet();
    }

    public Collection<PositionState> seats() {
        return coordinateToPositionStateMap.values();
    }

    public boolean isInRange(Coordinate coordinate) {
        return coordinateToPositionStateMap.containsKey(coordinate);
    }
}
