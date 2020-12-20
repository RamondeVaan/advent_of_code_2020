package nl.ramondevaan.aoc2020.Day20;

import lombok.Getter;
import nl.ramondevaan.aoc2020.util.ArrayUtil;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Tiles {
    @Getter
    private final Set<Long> cornerTileIds;
    private final int[][] values;
    public final int fieldWidth;

    public Tiles(Set<Long> cornerTileIds, int[][] values) {
        this.cornerTileIds = cornerTileIds;
        this.values = values;
        fieldWidth = values[0].length;
    }

    public String toImage() {
        return Arrays.stream(values)
                .map(codePoints -> new String(codePoints, 0, codePoints.length))
                .collect(Collectors.joining("\n"));
    }

    public Tiles flipLeftRight() {
        return new Tiles(cornerTileIds, ArrayUtil.flipLeftRight(values));
    }

    public Tiles rotateRight() {
        return new Tiles(cornerTileIds, ArrayUtil.rotateRight(values));
    }
}
