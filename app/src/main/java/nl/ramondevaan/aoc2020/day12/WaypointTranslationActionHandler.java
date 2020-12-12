package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class WaypointTranslationActionHandler implements ActionHandler {

    Vector2i translation;

    @Override
    public BoatState handleAction(BoatState boatState, Action action) {
        Vector2i newRelativeWaypoint = boatState.relativeWaypoint.add(translation, action.value);
        return new BoatState(boatState.position, boatState.direction, newRelativeWaypoint);
    }
}
