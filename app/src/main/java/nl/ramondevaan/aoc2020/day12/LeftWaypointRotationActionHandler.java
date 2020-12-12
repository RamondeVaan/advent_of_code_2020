package nl.ramondevaan.aoc2020.day12;

public class LeftWaypointRotationActionHandler implements ActionHandler {

    @Override
    public BoatState handleAction(BoatState boatState, Action action) {
        int offset = (action.getValue() / 90) % 4;

        Vector2i newRelativeWaypoint;

        switch (offset) {
            case 1:
                newRelativeWaypoint = Vector2i.of(-boatState.relativeWaypoint.y, boatState.relativeWaypoint.x);
                break;
            case 2:
                newRelativeWaypoint = Vector2i.of(-boatState.relativeWaypoint.x, -boatState.relativeWaypoint.y);
                break;
            case 3:
                newRelativeWaypoint = Vector2i.of(boatState.relativeWaypoint.y, -boatState.relativeWaypoint.x);
                break;
            default:
                newRelativeWaypoint = boatState.relativeWaypoint;
        }

        return new BoatState(boatState.position, boatState.direction, newRelativeWaypoint);
    }
}
