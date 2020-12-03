package nl.ramondevaan.aoc2020.day03;

import lombok.Value;

@Value
public class Coordinate {
    public int x;
    public int y;

    public Coordinate add(int xDiff, int yDiff) {
        return new Coordinate(x + xDiff, y + yDiff);
    }
}
