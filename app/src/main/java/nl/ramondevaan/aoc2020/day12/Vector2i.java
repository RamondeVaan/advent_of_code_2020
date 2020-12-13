package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class Vector2i {
    public int x;
    public int y;

    public Vector2i add(Vector2i other) {
        return new Vector2i(this.x + other.x, this.y + other.y);
    }

    public Vector2i add(Vector2i other, int multiplicationFactor) {
        return new Vector2i(this.x + other.x * multiplicationFactor, this.y + other.y * multiplicationFactor);
    }

    public Vector2i rotateRight(int times) {
        return rotate(Math.floorMod(times, 4));
    }

    public Vector2i rotateLeft(int times) {
        return rotate(4 - Math.floorMod(times, 4));
    }

    private Vector2i rotate(int times) {
        int xNew = this.x;
        int yNew = this.y;
        int temp;

        for (int i = 0; i < times; i++) {
            temp = yNew;
            yNew = -xNew;
            xNew = temp;
        }

        return new Vector2i(xNew, yNew);
    }

    public static Vector2i of(int x, int y) {
        return new Vector2i(x, y);
    }
}
