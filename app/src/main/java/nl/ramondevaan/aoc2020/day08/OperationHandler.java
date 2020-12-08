package nl.ramondevaan.aoc2020.day08;

public interface OperationHandler {
    State apply(State state, int argument);
}
