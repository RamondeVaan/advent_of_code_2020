package nl.ramondevaan.aoc2020.day03;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;

public class TreeMapParser implements Parser<List<String>, TreeMap> {

    @Override
    public TreeMap parse(List<String> lines) {
        int length = lines.size();
        int width = lines.get(0).length();
        boolean[][] isTree = new boolean[length][width];

        for (int y = 0; y < length; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                isTree[y][x] = line.charAt(x) == '#';
            }
        }

        return new TreeMap(width, length, isTree);
    }
}
