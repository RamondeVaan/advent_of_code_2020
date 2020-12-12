package nl.ramondevaan.aoc2020.day12;

public interface ActionHandler {

    BoatState handleAction(BoatState boatState, Action action);
}
