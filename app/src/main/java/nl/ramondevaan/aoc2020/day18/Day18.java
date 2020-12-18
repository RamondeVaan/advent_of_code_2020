package nl.ramondevaan.aoc2020.day18;

import nl.ramondevaan.aoc2020.day18.token.Token;
import nl.ramondevaan.aoc2020.day18.token.TokenParser;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.stream.Collectors;

public class Day18 {

    private final List<Expression> expressions;
    private final List<List<Token>> tokenizedExpressions;

    public Day18(List<String> lines) {
        Parser<String, Expression> parser = new ExpressionParser();
        Parser<String, List<Token>> tokenParser = new TokenParser();

        tokenizedExpressions = lines.stream().map(tokenParser::parse).collect(Collectors.toList());
        expressions = lines.stream().map(parser::parse).collect(Collectors.toList());

    }

    public long solve1() {
        return expressions.stream().mapToLong(Expression::compute).sum();
    }

    public long solve2() {
        Solver solver = new Solver();
        return tokenizedExpressions.stream().mapToLong(solver::solve).sum();
    }
}
