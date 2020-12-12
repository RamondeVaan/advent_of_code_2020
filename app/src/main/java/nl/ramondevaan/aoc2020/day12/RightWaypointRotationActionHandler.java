package nl.ramondevaan.aoc2020.day12;

public class RightWaypointRotationActionHandler implements ActionHandler {

    @Override
    public BoatState handleAction(BoatState boatState, int value) {
        int offset = (value / 90) % 4;

        Vector2i newRelativeWaypoint = boatState.relativeWaypoint.rotateClockwise(offset);

        return new BoatState(boatState.position, boatState.direction, newRelativeWaypoint);
    }
}
