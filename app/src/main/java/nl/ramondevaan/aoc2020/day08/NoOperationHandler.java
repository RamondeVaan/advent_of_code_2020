package nl.ramondevaan.aoc2020.day08;

public class NoOperationHandler implements OperationHandler {
    @Override
    public State apply(State state, int argument) {
        return state.incrementProgramCounter(1);
    }
}
