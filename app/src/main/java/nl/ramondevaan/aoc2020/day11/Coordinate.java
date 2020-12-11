package nl.ramondevaan.aoc2020.day11;

import lombok.Value;

@Value
public class Coordinate {
    public int x;
    public int y;

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }
}
