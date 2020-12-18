package nl.ramondevaan.aoc2020.day18.simplifier;

import nl.ramondevaan.aoc2020.day18.token.Token;

import java.util.List;

public interface Simplifier {
    List<Token> simplify(List<Token> tokens, int index);
}
