package nl.ramondevaan.aoc2020.day18;

import java.util.function.BiFunction;

public interface Operator {

    int getPrecedence();

    long apply(long left, long right);

    static Operator of(int precedence, BiFunction<Long, Long, Long> function) {
        return new Operator() {
            @Override
            public int getPrecedence() {
                return precedence;
            }

            @Override
            public long apply(long left, long right) {
                return function.apply(left, right);
            }
        };
    }
}
