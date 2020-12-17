package nl.ramondevaan.aoc2020.day17;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Cubes {
    public final List<Range> ranges;
    public final Set<CoordinateN> activeCubes;

    public boolean isActive(CoordinateN coordinate) {
        return activeCubes.contains(coordinate);
    }

    public static CubesBuilder builder(int dimension) {
        return new CubesBuilder(dimension);
    }

    public static class CubesBuilder {
        private final List<Range> ranges;
        private final Set<CoordinateN> activeCubes;

        public CubesBuilder(int dimension) {
            ranges = IntStream.range(0, dimension)
                    .mapToObj(dimensionIndex -> new EmptyRange())
                    .collect(Collectors.toList());
            activeCubes = new HashSet<>();
        }

        public CubesBuilder addActiveCube(CoordinateN coordinate) {
            for (int dimensionIndex = 0; dimensionIndex < ranges.size(); dimensionIndex++) {
                int dimensionCoordinate = coordinate.getCoordinate(dimensionIndex);
                ranges.set(dimensionIndex,
                        ranges.get(dimensionIndex).stretch(dimensionCoordinate - 1, dimensionCoordinate + 1));
            }

            activeCubes.add(coordinate);

            return this;
        }

        public Cubes build() {
            return new Cubes(Collections.unmodifiableList(ranges), Collections.unmodifiableSet(activeCubes));
        }
    }
}
