package nl.ramondevaan.aoc2020.Day20;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nl.ramondevaan.aoc2020.util.ArrayUtil;

import java.util.List;
import java.util.Map;

@Value
@EqualsAndHashCode(of = "id")
public class Tile {

    public long id;
    int[][] data;
    public Map<Side, List<Integer>> edgesClockwise;
    public Map<Side, List<Integer>> edgesCounterClockwise;

    public Tile rotateRight(int times) {
        if (times == 0) {
            return this;
        }

        int rot = Math.floorMod(times, 4);

        if (rot == 2) {
            return rotateTwice();
        }
        if (rot < 2) {
            return rotateRight();
        }

        return rotateLeft();
    }

    public Tile rotateRight() {
        int[][] newData = ArrayUtil.rotateRight(data);

        Map<Side, List<Integer>> newEdgesClockwise = Map.of(
                Side.TOP, edgesClockwise.get(Side.LEFT),
                Side.RIGHT, edgesClockwise.get(Side.TOP),
                Side.BOTTOM, edgesClockwise.get(Side.RIGHT),
                Side.LEFT, edgesClockwise.get(Side.BOTTOM)
        );
        Map<Side, List<Integer>> newEdgesCounterClockwise = Map.of(
                Side.TOP, edgesCounterClockwise.get(Side.LEFT),
                Side.RIGHT, edgesCounterClockwise.get(Side.TOP),
                Side.BOTTOM, edgesCounterClockwise.get(Side.RIGHT),
                Side.LEFT, edgesCounterClockwise.get(Side.BOTTOM)
        );

        return new Tile(this.id, newData, newEdgesClockwise, newEdgesCounterClockwise);
    }

    public Tile rotateLeft() {
        int[][] newData = ArrayUtil.rotateLeft(data);

        Map<Side, List<Integer>> newEdgesClockwise = Map.of(
                Side.TOP, edgesClockwise.get(Side.RIGHT),
                Side.RIGHT, edgesClockwise.get(Side.BOTTOM),
                Side.BOTTOM, edgesClockwise.get(Side.LEFT),
                Side.LEFT, edgesClockwise.get(Side.TOP)
        );
        Map<Side, List<Integer>> newEdgesCounterClockwise = Map.of(
                Side.TOP, edgesCounterClockwise.get(Side.RIGHT),
                Side.RIGHT, edgesCounterClockwise.get(Side.BOTTOM),
                Side.BOTTOM, edgesCounterClockwise.get(Side.LEFT),
                Side.LEFT, edgesCounterClockwise.get(Side.TOP)
        );

        return new Tile(this.id, newData, newEdgesClockwise, newEdgesCounterClockwise);
    }

    public Tile rotateTwice() {
        int[][] newData = ArrayUtil.rotateTwice(data);

        Map<Side, List<Integer>> newEdgesClockwise = Map.of(
                Side.TOP, edgesClockwise.get(Side.BOTTOM),
                Side.RIGHT, edgesClockwise.get(Side.LEFT),
                Side.BOTTOM, edgesClockwise.get(Side.TOP),
                Side.LEFT, edgesClockwise.get(Side.RIGHT)
        );
        Map<Side, List<Integer>> newEdgesCounterClockwise = Map.of(
                Side.TOP, edgesCounterClockwise.get(Side.BOTTOM),
                Side.RIGHT, edgesCounterClockwise.get(Side.LEFT),
                Side.BOTTOM, edgesCounterClockwise.get(Side.TOP),
                Side.LEFT, edgesCounterClockwise.get(Side.RIGHT)
        );

        return new Tile(this.id, newData, newEdgesClockwise, newEdgesCounterClockwise);
    }

    public Tile flipLeftRight() {
        int[][] newData = ArrayUtil.flipLeftRight(data);

        Map<Side, List<Integer>> newEdgesClockwise = Map.of(
                Side.TOP, edgesCounterClockwise.get(Side.TOP),
                Side.RIGHT, edgesCounterClockwise.get(Side.LEFT),
                Side.BOTTOM, edgesCounterClockwise.get(Side.BOTTOM),
                Side.LEFT, edgesCounterClockwise.get(Side.RIGHT)
        );
        Map<Side, List<Integer>> newEdgesCounterClockwise = Map.of(
                Side.TOP, edgesClockwise.get(Side.TOP),
                Side.RIGHT, edgesClockwise.get(Side.LEFT),
                Side.BOTTOM, edgesClockwise.get(Side.BOTTOM),
                Side.LEFT, edgesClockwise.get(Side.RIGHT)
        );

        return new Tile(this.id, newData, newEdgesClockwise, newEdgesCounterClockwise);
    }

    public Tile flipTopBottom() {
        int[][] newData = ArrayUtil.flipTopBottom(data);

        Map<Side, List<Integer>> newEdgesClockwise = Map.of(
                Side.TOP, edgesCounterClockwise.get(Side.BOTTOM),
                Side.RIGHT, edgesCounterClockwise.get(Side.RIGHT),
                Side.BOTTOM, edgesCounterClockwise.get(Side.TOP),
                Side.LEFT, edgesCounterClockwise.get(Side.LEFT)
        );
        Map<Side, List<Integer>> newEdgesCounterClockwise = Map.of(
                Side.TOP, edgesClockwise.get(Side.BOTTOM),
                Side.RIGHT, edgesClockwise.get(Side.RIGHT),
                Side.BOTTOM, edgesClockwise.get(Side.TOP),
                Side.LEFT, edgesClockwise.get(Side.LEFT)
        );

        return new Tile(this.id, newData, newEdgesClockwise, newEdgesCounterClockwise);
    }
}
