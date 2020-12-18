package nl.ramondevaan.aoc2020.day18.simplifier;

public class AddSimplifier extends BinarySimplifier {
    @Override
    protected long apply(long left, long right) {
        return left + right;
    }
}
