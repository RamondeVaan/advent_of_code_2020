package nl.ramondevaan.aoc2020.day24;

import lombok.Value;

import java.util.Arrays;
import java.util.stream.Stream;

@Value(staticConstructor = "of")
public class Coordinate {
    public int x;
    public int y;
    public int z;

    public Coordinate neighbor(Direction direction) {
        return new Coordinate(this.x + direction.x, this.y + direction.y, this.z + direction.z);
    }

    public Stream<Coordinate> neighbors() {
        return Arrays.stream(Direction.values()).map(this::neighbor);
    }
}
