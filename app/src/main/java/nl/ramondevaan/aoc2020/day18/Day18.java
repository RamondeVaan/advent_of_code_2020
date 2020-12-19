package nl.ramondevaan.aoc2020.day18;

import java.util.List;

public class Day18 {

    private final List<String> lines;

    public Day18(List<String> lines) {
        this.lines = lines;
    }

    public long solve1() {
        StringFunction leftToRight = new OperatorStringFunction();
        ExpressionEvaluator evaluator = new ExpressionEvaluator(List.of(new RemoveParenthesesStringFunction(),
                new ParenthesesStringFunction(List.of(leftToRight)), leftToRight));
        return lines.stream().mapToLong(evaluator::evaluate).sum();
    }

    public long solve2() {
        StringFunction add = new AddStringFunction();
        StringFunction multiply = new MultiplyStringFunction();
        ExpressionEvaluator evaluator = new ExpressionEvaluator(List.of(new RemoveParenthesesStringFunction(),
                new ParenthesesStringFunction(List.of(add, multiply)), add, multiply));
        return lines.stream().mapToLong(evaluator::evaluate).sum();
    }
}
