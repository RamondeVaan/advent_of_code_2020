package nl.ramondevaan.aoc2020.day08;

import lombok.Value;

@Value
public class State {
    int programCounter;
    long accumulator;

    public State withAccumulator(long accumulator) {
        return new State(this.programCounter, accumulator);
    }

    public State withProgramCounter(int programCounter) {
        return new State(programCounter, this.accumulator);
    }

    public State increment(int programCounterIncrement, long accumulatorIncrement) {
        return new State(this.programCounter + programCounterIncrement, this.accumulator + accumulatorIncrement);
    }

    public State incrementAccumulator(long increment) {
        return new State(this.programCounter, this.accumulator + increment);
    }

    public State incrementProgramCounter(int increment) {
        return new State(this.programCounter + increment, this.accumulator);
    }

    public static State copyOf(State state) {
        return new State(state.programCounter, state.accumulator);
    }
}
