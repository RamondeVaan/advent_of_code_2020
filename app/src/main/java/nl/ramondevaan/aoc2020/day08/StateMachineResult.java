package nl.ramondevaan.aoc2020.day08;

import lombok.Value;

@Value
public class StateMachineResult {
    StateMachineResultType type;
    State finalState;
}
