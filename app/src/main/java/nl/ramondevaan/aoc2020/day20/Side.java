package nl.ramondevaan.aoc2020.day20;

import java.util.Map;

public enum Side {
    TOP(0), RIGHT(1), BOTTOM(2), LEFT(3);

    private static final Map<Side, Side> complementMap = Map.of(
            TOP, BOTTOM,
            BOTTOM, TOP,
            LEFT, RIGHT,
            RIGHT, LEFT
    );

    private final int clockwiseOrder;

    Side(int clockwiseOrder) {
        this.clockwiseOrder = clockwiseOrder;
    }

    public Side complement() {
        return complementMap.get(this);
    }

    public int clockwiseOrder() {
        return this.clockwiseOrder;
    }
}
