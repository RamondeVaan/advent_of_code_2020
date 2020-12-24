package nl.ramondevaan.aoc2020.day24;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day24 {

    private final Set<Coordinate> initialBlackTiles;

    public Day24(List<String> lines) {
        Parser<String, List<Direction>> parser = new PathParser();
        List<List<Direction>> paths = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());

        HashSet<Coordinate> blackTiles = new HashSet<>();

        for (List<Direction> path : paths) {
            Coordinate coordinate = Coordinate.of(0, 0, 0);

            for (Direction direction : path) {
                coordinate = coordinate.neighbor(direction);
            }

            if (!blackTiles.add(coordinate)) {
                blackTiles.remove(coordinate);
            }
        }

        this.initialBlackTiles = Collections.unmodifiableSet(blackTiles);
    }

    public int solve1() {
        return initialBlackTiles.size();
    }

    public int solve2() {
        class Holder {
            Set<Coordinate> blackTiles;
        }

        Holder holder = new Holder();
        holder.blackTiles = initialBlackTiles;

        for (int i = 0; i < 100; i++) {
            Map<Coordinate, Long> collect = holder.blackTiles.stream().flatMap(Coordinate::neighbors)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            holder.blackTiles.forEach(coordinate -> collect.putIfAbsent(coordinate, 0L));

            holder.blackTiles = collect.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2 ||
                            (entry.getValue() == 1 && holder.blackTiles.contains(entry.getKey())))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toUnmodifiableSet());
        }

        return holder.blackTiles.size();
    }
}
