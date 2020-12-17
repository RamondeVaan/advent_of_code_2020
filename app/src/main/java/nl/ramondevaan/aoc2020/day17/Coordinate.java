package nl.ramondevaan.aoc2020.day17;

import lombok.Value;
import lombok.With;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Value(staticConstructor = "of")
@With
public class Coordinate {
    public int x;
    public int y;
    public int z;

    public Stream<Coordinate> neighbors(int distance) {
        int dist = Math.abs(distance);
        int xMin = x - dist;
        int xMax = x + dist;
        int yMin = y - dist;
        int yMax = y + dist;
        int zMin = z - dist;
        int zMax = z + dist;

        Stream<Coordinate> zBefore = IntStream.range(zMin, z).boxed()
                .flatMap(z -> IntStream.rangeClosed(yMin, yMax).boxed()
                        .flatMap(y -> IntStream.rangeClosed(xMin, xMax)
                                .mapToObj(x -> Coordinate.of(x, y, z))));

        Stream<Coordinate> yBefore = IntStream.range(yMin, y).boxed()
                .flatMap(y -> IntStream.rangeClosed(xMin, xMax)
                        .mapToObj(x -> Coordinate.of(x, y, z)));

        Stream<Coordinate> xBefore = IntStream.range(xMin, x).mapToObj(x -> Coordinate.of(x, y, z));

        Stream<Coordinate> xAfter = IntStream.rangeClosed(x + 1, xMax).mapToObj(x -> Coordinate.of(x, y, z));

        Stream<Coordinate> yAfter = IntStream.rangeClosed(y + 1, yMax).boxed()
                .flatMap(y -> IntStream.rangeClosed(xMin, xMax)
                        .mapToObj(x -> Coordinate.of(x, y, z)));

        Stream<Coordinate> zAfter = IntStream.rangeClosed(z + 1, zMax).boxed()
                .flatMap(z -> IntStream.rangeClosed(yMin, yMax).boxed()
                        .flatMap(y -> IntStream.rangeClosed(xMin, xMax)
                                .mapToObj(x -> Coordinate.of(x, y, z))));

        Stream<Coordinate> c1 = Stream.concat(zBefore, yBefore);
        Stream<Coordinate> c2 = Stream.concat(c1, xBefore);
        Stream<Coordinate> c3 = Stream.concat(xAfter, yAfter);
        Stream<Coordinate> c4 = Stream.concat(c3, zAfter);

        return Stream.concat(c2, c4);
    }

    public static Stream<Coordinate> range(Range xRange, Range yRange, Range zRange) {
        return zRange.stream().boxed()
                .flatMap(z -> yRange.stream().boxed()
                        .flatMap(y -> xRange.stream()
                                .mapToObj(x -> Coordinate.of(x, y, z))));
    }
}
