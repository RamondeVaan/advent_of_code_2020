package nl.ramondevaan.aoc2020.day18;

import lombok.Value;

@Value
public class AddExpression implements Expression {
    Expression left;
    Expression right;


    @Override
    public long compute() {
        return left.compute() + right.compute();
    }
}
