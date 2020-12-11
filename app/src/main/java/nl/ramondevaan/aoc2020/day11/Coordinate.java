package nl.ramondevaan.aoc2020.day11;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }
}
