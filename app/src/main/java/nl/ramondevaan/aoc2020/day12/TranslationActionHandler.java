package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class TranslationActionHandler implements ActionHandler {

    Vector2i translation;

    @Override
    public BoatState handleAction(BoatState boatState, Action action) {
        Vector2i newPosition = boatState.position.add(translation, action.value);
        return new BoatState(newPosition, boatState.direction, boatState.relativeWaypoint);
    }
}
