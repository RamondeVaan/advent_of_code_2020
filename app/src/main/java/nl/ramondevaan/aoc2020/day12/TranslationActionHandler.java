package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class TranslationActionHandler implements ActionHandler {

    Vector2i translation;

    @Override
    public BoatState handleAction(BoatState boatState, int value) {
        Vector2i newPosition = boatState.position.add(translation, value);
        return new BoatState(newPosition, boatState.direction, boatState.relativeWaypoint);
    }
}
