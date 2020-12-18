package nl.ramondevaan.aoc2020.day18.simplifier;

import nl.ramondevaan.aoc2020.day18.token.NumberToken;
import nl.ramondevaan.aoc2020.day18.token.Token;

import java.util.ArrayList;
import java.util.List;

public abstract class BinarySimplifier implements Simplifier {
    @Override
    public List<Token> simplify(List<Token> tokens, int index) {
        int leftIndex = index - 1;
        int rightIndex = index + 1;

        long left = ((NumberToken) tokens.get(leftIndex)).getValue();
        long right = ((NumberToken) tokens.get(rightIndex)).getValue();
        long result = apply(left, right);

        List<Token> ret = new ArrayList<>(tokens.subList(0, leftIndex));
        ret.add(new NumberToken(result));
        ret.addAll(new ArrayList<>(tokens.subList(rightIndex + 1, tokens.size())));

        return ret;
    }

    protected abstract long apply(long left, long right);
}
