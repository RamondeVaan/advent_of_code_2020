package nl.ramondevaan.aoc2020.day03;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.stream.Stream;

public class Day03 {

    private final TreeMap treeMap;

    public Day03(List<String> lines) {
        Parser<List<String>, TreeMap> parser = new TreeMapParser();
        this.treeMap = parser.parse(lines);
    }

    public long solve1() {
        return countForPath(treeMap, new Coordinate(3, 1));
    }

    public long solve2() {
        return Stream.of(new Coordinate(1, 1),
                new Coordinate(3, 1),
                new Coordinate(5, 1),
                new Coordinate(7, 1),
                new Coordinate(1, 2))
                .map(step -> countForPath(treeMap, step))
                .reduce(1L, (current, count) -> current * count);
    }

    private long countForPath(TreeMap treeMap, Coordinate step) {
        return coordinates(step.x, step.y, treeMap.getLength())
                .filter(treeMap::isTree)
                .count();
    }

    private Stream<Coordinate> coordinates(int xDif, int yDif, int yMax) {
        return Stream.iterate(
                new Coordinate(0, 0),
                coordinate -> coordinate.y < yMax,
                coordinate -> coordinate.add(xDif, yDif)
        );
    }
}
