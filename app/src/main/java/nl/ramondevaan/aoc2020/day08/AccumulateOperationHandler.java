package nl.ramondevaan.aoc2020.day08;

public class AccumulateOperationHandler implements OperationHandler {
    @Override
    public State apply(State state, int argument) {
        return state.increment(1, argument);
    }
}
