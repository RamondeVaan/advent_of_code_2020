package nl.ramondevaan.aoc2020.day12;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static nl.ramondevaan.aoc2020.day12.Direction.*;

public class Day12 {

    private final List<Action> actions;
    private final BoatState initialBoatState;

    public Day12(List<String> lines) {
        Parser<String, Action> parser = new ActionParser();
        actions = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
        initialBoatState = new BoatState(Vector2i.of(0, 0), EAST, Vector2i.of(10, 1));
    }

    public long solve1() {
        return solve(Map.of(
                ActionType.N, new TranslationActionHandler(NORTH.translation),
                ActionType.E, new TranslationActionHandler(EAST.translation),
                ActionType.S, new TranslationActionHandler(SOUTH.translation),
                ActionType.W, new TranslationActionHandler(WEST.translation),
                ActionType.L, new RotationActionHandler(List.of(NORTH, WEST, SOUTH, EAST)),
                ActionType.R, new RotationActionHandler(List.of(NORTH, EAST, SOUTH, WEST)),
                ActionType.F, new ForwardActionHandler()
        ));
    }

    public long solve2() {
        return solve(Map.of(
                ActionType.N, new WaypointTranslationActionHandler(NORTH.translation),
                ActionType.E, new WaypointTranslationActionHandler(EAST.translation),
                ActionType.S, new WaypointTranslationActionHandler(SOUTH.translation),
                ActionType.W, new WaypointTranslationActionHandler(WEST.translation),
                ActionType.L, new LeftWaypointRotationActionHandler(),
                ActionType.R, new RightWaypointRotationActionHandler(),
                ActionType.F, new ForwardToWaypointActionHandler()
        ));
    }

    private long solve(Map<ActionType, ActionHandler> handlerMap) {
        BoatState boatState = initialBoatState;

        for (Action action : actions) {
            boatState = handlerMap.get(action.actionType).handleAction(boatState, action);
        }

        return distanceTravelled(boatState);
    }

    private long distanceTravelled(BoatState boatState) {
        return Math.abs((long) boatState.position.x) + Math.abs((long) boatState.position.y);
    }
}
