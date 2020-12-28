package nl.ramondevaan.aoc2020.day18;

import java.util.List;
import java.util.Map;

public class Day18 {

    private final List<String> lines;

    public Day18(List<String> lines) {
        this.lines = lines;
    }

    public long solve1() {
        Map<Character, Operator> operatorMap = Map.of(
                '+', Operator.of(1, Long::sum),
                '*', Operator.of(1, (left, right) -> left * right)
        );
        ExpressionParser parser = new ExpressionParser(operatorMap);

        return lines.stream().mapToLong(parser::parse).sum();
    }

    public long solve2() {
        Map<Character, Operator> operatorMap = Map.of(
                '+', Operator.of(2, Long::sum),
                '*', Operator.of(1, (left, right) -> left * right)
        );
        ExpressionParser parser = new ExpressionParser(operatorMap);

        return lines.stream().mapToLong(parser::parse).sum();
    }
}
