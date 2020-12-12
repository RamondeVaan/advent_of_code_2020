package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class ForwardActionHandler implements ActionHandler {

    @Override
    public BoatState handleAction(BoatState boatState, int value) {
        Vector2i translation = boatState.direction.translation;
        Vector2i nextPosition = boatState.position.add(translation, value);

        return new BoatState(nextPosition, boatState.direction, boatState.relativeWaypoint);
    }
}
