package nl.ramondevaan.aoc2020.day18.simplifier;

import lombok.Value;
import nl.ramondevaan.aoc2020.day18.token.Token;

import java.util.List;

@Value
public class ParenthesesSimplifier implements Simplifier {

    Simplifier simplifier;

    @Override
    public List<Token> simplify(List<Token> tokens, int index) {
        return null;
    }
}
