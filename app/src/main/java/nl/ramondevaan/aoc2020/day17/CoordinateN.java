package nl.ramondevaan.aoc2020.day17;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
        final int absDistance = Math.abs(distance);
        List<Range> ranges = Arrays.stream(coordinatePerDimension)
                .mapToObj(value -> RangeImpl.of(value - absDistance, value + absDistance))
                .collect(Collectors.toList());
        Set<CoordinateN> set = range(ranges).collect(Collectors.toSet());
        set.remove(this);
        return set.stream();
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

    public static CoordinateN of(int x, int y) {
        return new CoordinateN(new int[]{x, y});
    }
}
