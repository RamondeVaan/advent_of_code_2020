package nl.ramondevaan.aoc2020.Day20;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

@Value
@EqualsAndHashCode(of = "id")
public class Tile {

    long id;
    int[][] data;

    public Map<Side, int[]> getEdgesClockWise() {
        return Map.of(
                Side.TOP, data[0],
                Side.RIGHT, Arrays.stream(data).mapToInt(datum -> datum[data[0].length - 1]).toArray(),
                Side.BOTTOM, reverse(data[data.length - 1]),
                Side.LEFT, IntStream.rangeClosed(1, data.length)
                        .map(index -> data.length - index)
                        .map(index -> data[index][0]).toArray()
        );
    }

    public Map<Side, int[]> getEdgesCounterClockwise() {
        return Map.of(
                Side.TOP, reverse(data[0]),
                Side.LEFT, Arrays.stream(data).mapToInt(datum -> datum[0]).toArray(),
                Side.BOTTOM, data[data.length - 1],
                Side.RIGHT, IntStream.rangeClosed(1, data.length)
                        .map(index -> data.length - index)
                        .map(index -> data[index][data[0].length - 1]).toArray()
        );
    }

    private static int[] reverse(int[] arr) {
        int[] ret = new int[arr.length];

        int retIndex = ret.length - 1;
        for (int value : arr) {
            ret[retIndex--] = value;
        }

        return ret;
    }

    public int[] getEdge(Side side) {
        switch (side) {
            case TOP:
                return data[0];
            case BOTTOM:
                return data[data.length - 1];
            case LEFT:
                return Arrays.stream(data).mapToInt(datum -> datum[0]).toArray();
            case RIGHT:
                return Arrays.stream(data).mapToInt(datum -> datum[data[0].length - 1]).toArray();
        }

        throw new UnsupportedOperationException();
    }

    public boolean matchesClockwise(int[] edge) {
        for (int[] thisEdge : getEdgesClockWise().values()) {
            if (Arrays.equals(thisEdge, edge)) {
                return true;
            }
        }

        return false;
    }

    public boolean matchesCounterClockwise(int[] edge) {
        for (int[] thisEdge : getEdgesCounterClockwise().values()) {
            if (Arrays.equals(thisEdge, edge)) {
                return true;
            }
        }

        return false;
    }
}
