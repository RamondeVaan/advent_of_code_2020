package nl.ramondevaan.aoc2020.Day20;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;

public class TileParser implements Parser<List<String>, Tile> {

    @Override
    public Tile parse(List<String> lines) {
        long id = parseId(lines.get(0));
        int[][] data = parseData(lines.subList(1, lines.size()));

        return new Tile(id, data);
    }

    private long parseId(String idLine) {
        int colonIndex = idLine.indexOf(':', 7);
        String idString = idLine.substring(5, colonIndex);
        return Long.parseLong(idString);
    }

    private int[][] parseData(List<String> lines) {
        return lines.stream().map(line -> line.codePoints().toArray()).toArray(int[][]::new);
    }
}
