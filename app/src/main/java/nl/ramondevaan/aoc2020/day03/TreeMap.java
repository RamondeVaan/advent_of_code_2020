package nl.ramondevaan.aoc2020.day03;

import lombok.Value;

@Value
public class TreeMap {

    int width;
    int length;
    boolean[][] isTree;

    public boolean isTree(Coordinate coordinate) {
        return isTree[coordinate.y][coordinate.x % width];
    }
}
