package nl.ramondevaan.aoc2020.day18;

import lombok.Value;

@Value
public class ParenthesesExpression implements Expression {

    Expression expression;

    @Override
    public long compute() {
        return expression.compute();
    }
}
