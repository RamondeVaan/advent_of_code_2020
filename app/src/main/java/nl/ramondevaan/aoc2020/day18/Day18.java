package nl.ramondevaan.aoc2020.day18;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.stream.Collectors;

public class Day18 {

    private final List<Expression> expressions;

    public Day18(List<String> lines) {
        Parser<String, Expression> parser = new ExpressionParser();

        expressions = lines.stream().map(parser::parse).collect(Collectors.toList());
    }

    public long solve1() {
        return expressions.stream().mapToLong(Expression::compute).sum();
    }

    public long solve2() {
        return 0L;
    }
}
