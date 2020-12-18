package nl.ramondevaan.aoc2020.day18;

import lombok.Value;

@Value
public class NumberExpression implements Expression {

    long value;

    @Override
    public long compute() {
        return value;
    }
}
