package nl.ramondevaan.aoc2020.day12;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotationActionHandler implements ActionHandler {

    private final List<Direction> directionOrder;
    private final Map<Direction, Integer> index;

    public RotationActionHandler(List<Direction> directionOrder) {
        this.directionOrder = directionOrder;

        Map<Direction, Integer> tempIndex = new HashMap<>();
        int count = 0;

        for (Direction direction : directionOrder) {
            tempIndex.put(direction, count++);
        }

        this.index = Collections.unmodifiableMap(tempIndex);
    }

    @Override
    public BoatState handleAction(BoatState boatState, int value) {
        int start = index.get(boatState.direction);
        int offset = (value / 90);
        int nextIndex = (start + offset) % directionOrder.size();
        Direction next = directionOrder.get(nextIndex);

        return new BoatState(boatState.position, next, boatState.relativeWaypoint);
    }
}
