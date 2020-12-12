package nl.ramondevaan.aoc2020.day12;

public class ForwardToWaypointActionHandler implements ActionHandler {
    @Override
    public BoatState handleAction(BoatState boatState, Action action) {
        Vector2i newPosition = boatState.position.add(boatState.relativeWaypoint, action.value);

        return new BoatState(newPosition, boatState.direction, boatState.relativeWaypoint);
    }
}
