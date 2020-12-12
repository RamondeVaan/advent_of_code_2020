package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class WaypointTranslationActionHandler implements ActionHandler {

    Vector2i translation;

    @Override
    public BoatState handleAction(BoatState boatState, int value) {
        Vector2i newRelativeWaypoint = boatState.relativeWaypoint.add(translation, value);
        return new BoatState(boatState.position, boatState.direction, newRelativeWaypoint);
    }
}
