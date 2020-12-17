package nl.ramondevaan.aoc2020.day17;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinateN {
    private final int[] coordinatePerDimension;

    public CoordinateN toDimension(int dimension) {
        int[] newArray = new int[dimension];
        System.arraycopy(coordinatePerDimension, 0, newArray, 0,
                Math.min(newArray.length, coordinatePerDimension.length));
        return new CoordinateN(newArray);
    }

    public int getCoordinate(int dimension) {
        return coordinatePerDimension[dimension];
    }

    public Stream<CoordinateN> neighbors(int distance) {
        distance = Math.abs(distance);
        int dimension = coordinatePerDimension.length;

        Stream<CoordinateN> before = Stream.empty();
        Stream<CoordinateN> after = Stream.empty();

        for (int dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++) {
            Range[] rangesBefore = new Range[dimension];
            Range[] rangesAfter = new Range[dimension];

            for (int index = dimension - 1; index > dimensionIndex; index--) {
                int coordinate = coordinatePerDimension[index];
                Range range = RangeImpl.of(coordinate - distance, coordinate + distance);
                rangesBefore[index] = range;
                rangesAfter[index] = range;
            }

            int dimensionCoordinate = coordinatePerDimension[dimensionIndex];
            rangesBefore[dimensionIndex] = RangeImpl.of(dimensionCoordinate - distance, dimensionCoordinate - 1);
            rangesAfter[dimensionIndex] = RangeImpl.of(dimensionCoordinate + 1, dimensionCoordinate + distance);

            for (int index = dimensionIndex - 1; index >= 0; index--) {
                int coordinate = coordinatePerDimension[index];
                Range range = RangeImpl.of(coordinate, coordinate);
                rangesBefore[index] = range;
                rangesAfter[index] = range;
            }

            before = Stream.concat(before, range(rangesBefore));
            after = Stream.concat(range(rangesAfter), after);
        }

        return Stream.concat(before, after);
    }

    public static Stream<CoordinateN> range(Range... ranges) {
        return range(Arrays.asList(ranges));
    }

    public static Stream<CoordinateN> range(List<Range> ranges) {
        Stream<int[]> stream = Stream.of(new int[]{});

        for (Range range : ranges) {
            stream = stream.flatMap(array -> range.stream().mapToObj(coordinate -> {
                int[] newArray = new int[array.length + 1];
                System.arraycopy(array, 0, newArray, 0, array.length);
                newArray[array.length] = coordinate;
                return newArray;
            }));
        }

        return stream.map(CoordinateN::new);
    }

    public static CoordinateN of(int[] coordinatePerDimension) {
        int[] copy = new int[coordinatePerDimension.length];
        System.arraycopy(coordinatePerDimension, 0, copy, 0, copy.length);
        return new CoordinateN(copy);
    }

    public static CoordinateN of(int x, int y) {
        return new CoordinateN(new int[]{x, y});
    }
}
