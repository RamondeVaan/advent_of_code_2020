package nl.ramondevaan.aoc2020.day24;

public enum Direction {
    EAST(1, -1, 0), WEST(-1, 1, 0),
    NORTH_EAST(1, 0, -1), SOUTH_WEST(-1, 0, 1),
    SOUTH_EAST(0, -1, 1), NORTH_WEST(0, 1, -1);

    public final int x;
    public final int y;
    public final int z;


    Direction(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
