package nl.ramondevaan.aoc2020.day03;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Day03 {

    private final TreeMap trees;

    public static Day03Builder builder() {
        return new Day03Builder();
    }

    public long solve1() {
        return countForPath(new Coordinate(3, 1));
    }

    public long solve2() {
        return Stream.of(new Coordinate(1, 1),
                new Coordinate(3, 1),
                new Coordinate(5, 1),
                new Coordinate(7, 1),
                new Coordinate(1, 2))
                .map(this::countForPath)
                .reduce(1L, (current, count) -> current * count);
    }

    private long countForPath(Coordinate step) {
        return coordinates(step.x, step.y)
                .filter(trees::isTree)
                .count();
    }

    private Stream<Coordinate> coordinates(int xDif, int yDif) {
        return Stream.iterate(
                new Coordinate(0, 0),
                coordinate -> coordinate.y < trees.getLength(),
                coordinate -> new Coordinate(coordinate.x + xDif, coordinate.y + yDif)
        );
    }

    public static class Day03Builder {
        private List<String> trees;

        Day03Builder() {
        }

        public Day03Builder trees(List<String> trees) {
            this.trees = trees;
            return this;
        }

        private TreeMap parseTrees() {
            int length = trees.size();
            int width = trees.get(0).length();
            boolean[][] isTree = new boolean[length][width];

            for (int y = 0; y < length; y++) {
                String line = trees.get(y);
                for (int x = 0; x < width; x++) {
                    isTree[y][x] = line.charAt(x) == '#';
                }
            }

            return new TreeMap(width, length, isTree);
        }

        public Day03 build() {
            return new Day03(parseTrees());
        }

        public String toString() {
            return "Day03.Day03Builder(trees=" + this.trees.toString() + ")";
        }
    }
}
