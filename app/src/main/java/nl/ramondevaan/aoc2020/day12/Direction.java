package nl.ramondevaan.aoc2020.day12;

public enum Direction {
    NORTH(Vector2i.of(0, 1)),
    EAST(Vector2i.of(1, 0)),
    SOUTH(Vector2i.of(0, -1)),
    WEST(Vector2i.of(-1, 0));

    public final Vector2i translation;

    Direction(Vector2i translation) {
        this.translation = translation;
    }

    public Direction rotateRight(int times) {
        return Direction.values()[Math.floorMod(this.ordinal() + times, 4)];
    }

    public Direction rotateLeft(int times) {
        return Direction.values()[Math.floorMod(this.ordinal() - times, 4)];
    }
}
