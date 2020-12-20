package nl.ramondevaan.aoc2020.day20;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TileParser implements Parser<List<String>, Tile> {

    @Override
    public Tile parse(List<String> lines) {
        List<String> values = lines.subList(1, lines.size());
        List<String> core = values.subList(1, values.size() - 1);

        long id = parseId(lines.get(0));
        int[][] data = getData(core);
        Map<Side, List<Integer>> edgesClockwise = getEdgesClockwise(values);
        Map<Side, List<Integer>> edgesCounterClockwise = getEdgesCounterClockwise(edgesClockwise);

        return new Tile(id, data, edgesClockwise, edgesCounterClockwise);
    }

    private Map<Side, List<Integer>> getEdgesClockwise(List<String> values) {
        List<Integer> top = values.get(0).codePoints().boxed().collect(Collectors.toList());
        List<Integer> right = new ArrayList<>(values.size());
        List<Integer> bottom = values.get(values.size() - 1).codePoints().boxed().collect(Collectors.toList());
        List<Integer> left = new ArrayList<>(values.size());

        for (String line : values) {
            left.add(line.codePointAt(0));
            right.add(line.codePointAt(line.length() - 1));
        }

        Collections.reverse(bottom);
        Collections.reverse(left);

        top = Collections.unmodifiableList(top);
        right = Collections.unmodifiableList(right);
        bottom = Collections.unmodifiableList(bottom);
        left = Collections.unmodifiableList(left);

        return Map.of(
                Side.TOP, top,
                Side.RIGHT, right,
                Side.BOTTOM, bottom,
                Side.LEFT, left
        );
    }

    private Map<Side, List<Integer>> getEdgesCounterClockwise(Map<Side, List<Integer>> edgesClockwise) {
        return edgesClockwise.entrySet().stream().collect(Collectors.toUnmodifiableMap(
                Map.Entry::getKey,
                entry -> {
                    List<Integer> values = new ArrayList<>(entry.getValue());
                    Collections.reverse(values);
                    return Collections.unmodifiableList(values);
                }
        ));
    }

    private long parseId(String idLine) {
        int colonIndex = idLine.indexOf(':', 7);
        String idString = idLine.substring(5, colonIndex);
        return Long.parseLong(idString);
    }

    private int[][] getData(List<String> values) {
        int[][] data = new int[values.size()][values.size()];

        for (int i = 0; i < values.size(); i++) {
            String line = values.get(i);
            data[i] = line.substring(1, line.length() - 1).codePoints().toArray();
        }

        return data;
    }
}
