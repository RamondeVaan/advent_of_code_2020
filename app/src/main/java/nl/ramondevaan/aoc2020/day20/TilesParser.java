package nl.ramondevaan.aoc2020.day20;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TilesParser implements Parser<List<String>, Tiles> {

    private final Parser<List<String>, Tile> parser = new TileParser();
    private final Partitioner<String> partitioner = new BlankStringPartitioner();

    @Override
    public Tiles parse(List<String> lines) {
        List<List<String>> partitions = partitioner.partition(lines);

        Set<Tile> tileMap = partitions.stream().map(parser::parse).collect(Collectors.toUnmodifiableSet());
        Table<Tile, Side, Tile> neighborTable = toNeighborTable(tileMap);

        Set<Long> cornerTileIds = getCornerTileIds(neighborTable);
        Tile[][] tiles = getTiles(neighborTable);
        int[][] data = getData(tiles);

        return new Tiles(cornerTileIds, data);
    }

    private Tile[][] getTiles(Table<Tile, Side, Tile> table) {
        Tile topLeftTile = getTopLeftCornerTile(table);

        long tilesWidth = Stream.iterate(topLeftTile, Objects::nonNull, tile -> table.get(tile, Side.RIGHT)).count();
        long tilesHeight = Stream.iterate(topLeftTile, Objects::nonNull, tile -> table.get(tile, Side.BOTTOM)).count();

        Tile[][] tiles = new Tile[(int) tilesHeight][(int) tilesWidth];

        int x;
        int y = 0;

        for (Tile leftMost = topLeftTile; leftMost != null; leftMost = table.get(leftMost, Side.BOTTOM)) {
            x = 0;
            for (Tile tile = leftMost; tile != null; tile = table.get(tile, Side.RIGHT)) {
                tiles[y][x] = tile;
                x++;
            }
            y++;
        }

        return tiles;
    }

    private int[][] getData(Tile[][] tiles) {
        int height = tiles.length;
        int width = tiles[0].length;
        int tileSize = tiles[0][0].size;

        int[][] data = new int[height * tileSize][width * tileSize];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x].copyData(data, x * tileSize, y * tileSize);
            }
        }

        return data;
    }

    private Tile getTopLeftCornerTile(Table<Tile, Side, Tile> neighborTable) {
        return neighborTable.rowMap().entrySet().stream()
                .filter(entry -> entry.getValue().get(Side.LEFT) == null && entry.getValue().get(Side.TOP) == null)
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    private Set<Long> getCornerTileIds(Table<Tile, Side, Tile> neighborTable) {
        return neighborTable.rowMap().entrySet().stream()
                .filter(entry -> entry.getValue().size() == 2)
                .map(Map.Entry::getKey)
                .map(Tile::getId)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Table<Tile, Side, Tile> toNeighborTable(Set<Tile> tiles) {
        Map<Long, Tile> tileMap = tiles.stream().collect(Collectors.toMap(Tile::getId, Function.identity()));
        Table<Tile, Side, Tile> tileNeighborTable = HashBasedTable.create();
        Multimap<Long, Side> remainingSidesMap = HashMultimap.create();
        tileMap.keySet().forEach(id -> remainingSidesMap.putAll(id, Arrays.asList(Side.values())));

        Set<Long> idsToCheck = tileMap.keySet().stream().findFirst().map(Collections::singleton).orElseThrow();
        Set<Long> nextToCheck;

        while (!idsToCheck.isEmpty()) {
            nextToCheck = new HashSet<>();

            for (long id : idsToCheck) {
                Tile tile = tileMap.remove(id);
                Collection<Side> remainingSides = remainingSidesMap.get(id);
                for (Side side : remainingSides) {
                    List<Integer> edge = tile.edgesClockwise.get(side);
                    Side complement = side.complement();
                    Tile match = findMatch(complement, edge, tileMap, remainingSidesMap);

                    if (match != null) {
                        nextToCheck.add(match.id);
                        tileMap.put(match.id, match);
                        remainingSidesMap.remove(match.id, complement);
                        tileNeighborTable.put(tile, side, match);
                        tileNeighborTable.put(match, complement, tile);
                    }
                }
                remainingSidesMap.removeAll(id);
            }

            idsToCheck = nextToCheck;
        }

        return tileNeighborTable;
    }

    private Tile findMatch(Side target, List<Integer> edge, Map<Long, Tile> tileMap,
                           Multimap<Long, Side> remainingSidesMap) {
        for (Tile tile : tileMap.values()) {
            Collection<Side> remainingSides = remainingSidesMap.get(tile.id);

            for (Side side : remainingSides) {
                if (tile.edgesClockwise.get(side).equals(edge)) {
                    return rotateAndFlip(tile, side, target, true);
                }
                if (tile.edgesCounterClockwise.get(side).equals(edge)) {
                    return rotateAndFlip(tile, side, target, false);
                }
            }
        }

        return null;
    }

    private Tile rotateAndFlip(Tile tile, Side match, Side target, boolean flip) {
        int times = target.clockwiseOrder() - match.clockwiseOrder();

        Tile rotated = tile.rotateRight(times);

        if (flip) {
            if (target == Side.LEFT || target == Side.RIGHT) {
                return rotated.flipTopBottom();
            } else {
                return rotated.flipLeftRight();
            }
        }

        return rotated;
    }
}
