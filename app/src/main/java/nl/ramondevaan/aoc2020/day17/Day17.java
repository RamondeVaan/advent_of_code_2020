package nl.ramondevaan.aoc2020.day17;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Set;

public class Day17 {

    private static final int REPETITIONS = 6;
    private final Set<CoordinateN> initialActiveCubes;

    public Day17(List<String> lines) {
        Parser<List<String>, Set<CoordinateN>> parser = new CubesParser();
        initialActiveCubes = parser.parse(lines);
    }

    public long solve1() {
        return solve(3);
    }

    public long solve2() {
        return solve(4);
    }

    public long solve(int dimension) {
        Cubes cubes = getCubes(dimension);

        for (int i = 0; i < REPETITIONS; i++) {
            cubes = nextCubes(cubes);
        }

        return CoordinateN.range(cubes.ranges)
                .filter(cubes::isActive)
                .count();
    }

    private Cubes getCubes(int dimension) {
        Cubes.CubesBuilder builder = Cubes.builder(dimension);

        for (CoordinateN coordinate : initialActiveCubes) {
            builder.addActiveCube(coordinate.toDimension(dimension));
        }

        return builder.build();
    }

    private static Cubes nextCubes(Cubes cubes) {
        Cubes.CubesBuilder builder = Cubes.builder(cubes.ranges.size());

        CoordinateN.range(cubes.ranges).forEach(coordinate -> {
            boolean isActive = cubes.isActive(coordinate);
            long activeNeighbors = coordinate.neighbors(1).filter(cubes::isActive).count();

            if (activeNeighbors == 3L || (activeNeighbors == 2 && isActive)) {
                builder.addActiveCube(coordinate);
            }
        });

        return builder.build();
    }
}
