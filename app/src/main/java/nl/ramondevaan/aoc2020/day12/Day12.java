package nl.ramondevaan.aoc2020.day12;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static nl.ramondevaan.aoc2020.day12.Direction.*;

public class Day12 {

    private final List<Action> actions;
    private final Boat initialBoat;

    public Day12(List<String> lines) {
        Parser<String, Action> parser = new ActionParser();
        actions = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
        initialBoat = new Boat(Vector2i.of(0, 0), EAST, Vector2i.of(10, 1));
    }

    public long solve1() {
        return solve(Map.of(
                ActionType.N, (boat, value) -> boat.withPosition(boat.position.add(NORTH.translation, value)),
                ActionType.E, (boat, value) -> boat.withPosition(boat.position.add(EAST.translation, value)),
                ActionType.S, (boat, value) -> boat.withPosition(boat.position.add(SOUTH.translation, value)),
                ActionType.W, (boat, value) -> boat.withPosition(boat.position.add(WEST.translation, value)),
                ActionType.L, (boat, value) -> boat.withDirection(boat.direction.rotateLeft(value / 90)),
                ActionType.R, (boat, value) -> boat.withDirection(boat.direction.rotateRight(value / 90)),
                ActionType.F, (boat, value) -> boat.withPosition(boat.position.add(boat.direction.translation, value))
        ));
    }

    public long solve2() {
        return solve(Map.of(
                ActionType.N, (boat, value) -> boat.withWaypoint(boat.waypoint.add(NORTH.translation, value)),
                ActionType.E, (boat, value) -> boat.withWaypoint(boat.waypoint.add(EAST.translation, value)),
                ActionType.S, (boat, value) -> boat.withWaypoint(boat.waypoint.add(SOUTH.translation, value)),
                ActionType.W, (boat, value) -> boat.withWaypoint(boat.waypoint.add(WEST.translation, value)),
                ActionType.L, (boat, value) -> boat.withWaypoint(boat.waypoint.rotateLeft(value / 90)),
                ActionType.R, (boat, value) -> boat.withWaypoint(boat.waypoint.rotateRight(value / 90)),
                ActionType.F, (boat, value) -> boat.withPosition(boat.position.add(boat.waypoint, value))
        ));
    }

    private long solve(Map<ActionType, ActionHandler> handlerMap) {
        Boat boat = initialBoat;

        for (Action action : actions) {
            boat = handlerMap.get(action.actionType).handleAction(boat, action.value);
        }

        return distanceTravelled(boat);
    }

    private long distanceTravelled(Boat boat) {
        return Math.abs((long) boat.position.x) + Math.abs((long) boat.position.y);
    }
}
