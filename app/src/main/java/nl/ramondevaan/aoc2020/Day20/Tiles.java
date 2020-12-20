package nl.ramondevaan.aoc2020.Day20;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Tiles {
    @Getter
    private final Set<Long> cornerTileIds;
    private final int[][] values;

    public String toImage() {
        return Arrays.stream(values)
                .map(codePoints -> new String(codePoints, 0, codePoints.length))
                .collect(Collectors.joining("\n"));
    }
}
