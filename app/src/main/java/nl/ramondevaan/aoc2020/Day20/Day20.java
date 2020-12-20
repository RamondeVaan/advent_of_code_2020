package nl.ramondevaan.aoc2020.Day20;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;

public class Day20 {
    private final Tiles initialTiles;

    public Day20(List<String> lines) {
        Parser<List<String>, Tiles> parser = new TilesParser();
        initialTiles = parser.parse(lines);
    }

    public long solve1() {
        return initialTiles.getCornerTileIds().stream()
                .reduce(1L, (left, right) -> left * right);
    }

    public long solve2() {
        String s = initialTiles.toImage();

        return 0L;
    }
}
