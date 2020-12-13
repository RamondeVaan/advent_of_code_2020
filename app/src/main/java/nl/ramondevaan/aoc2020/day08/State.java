package nl.ramondevaan.aoc2020.day08;

import lombok.Value;

@Value
public class State {
    int programCounter;
    long accumulator;

    public State increment(int programCounterIncrement, long accumulatorIncrement) {
        return new State(this.programCounter + programCounterIncrement, this.accumulator + accumulatorIncrement);
    }

    public State increaseProgramCounter(int increment) {
        return new State(this.programCounter + increment, this.accumulator);
    }
}
