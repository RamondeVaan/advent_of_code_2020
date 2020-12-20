package nl.ramondevaan.aoc2020.Day20;

import com.google.common.collect.Multimap;
import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day20 {

    private final Set<Tile> tiles;

    public Day20(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        List<List<String>> partitions = partitioner.partition(lines);

        Parser<List<String>, Tile> parser = new TileParser();
        tiles = partitions.stream().map(parser::parse).collect(Collectors.toUnmodifiableSet());
    }

    public long solve1() {
        return tiles.stream()
                .filter(tile -> tile.getEdgesClockWise().values().stream()
                        .filter(edge -> tiles.stream()
                                .filter(otherTile -> otherTile.getId() != tile.getId())
                                .filter(otherTile -> otherTile.matchesCounterClockwise(edge) || otherTile.matchesClockwise(edge))
//                        .peek(otherTile -> System.out.printf("tile %d edge %s matches tile %d%n", tile.getId(), new String(edge, 0, edge.length), otherTile.getId()))
                                .count() == 0L)
                        .count() == 2L)
                .mapToLong(Tile::getId)
//                .peek(System.out::println)
                .reduce(1L, (left, right) -> left * right);
    }

    public long solve2() {
        Map<Long, Tile> tileMap = tiles.stream().collect(Collectors.toMap(Tile::getId, Function.identity()));
        Set<Long> tileIds = new HashSet<>(tileMap.keySet());
        Map<Side, Long> initialSideMap = Map.of(Side.LEFT, 0L, Side.TOP, 0L, Side.RIGHT, 0L, Side.BOTTOM, 0L);
        Map<Long, Map<Side, Long>> potentialNeighborCount = tileIds.stream().collect(Collectors.toMap(Function.identity(), id -> new HashMap<>(initialSideMap)));

        AtomicLong atomicLong = new AtomicLong(0L);

        for (long id : tileIds) {
            Tile tile = tileMap.remove(id);
            tile.getEdgesClockWise().forEach((side, edge) -> {
                long matchingTiles = tileMap.values().stream()
                        .filter(otherTile -> otherTile.matchesClockwise(edge) ||
                                otherTile.matchesCounterClockwise(edge))
                        .count();
                if(matchingTiles <= 1) {
                    atomicLong.incrementAndGet();
                }
            });
        }

        System.out.println(tiles.size());
        System.out.println(atomicLong.get());

        return 0L;
    }
}
