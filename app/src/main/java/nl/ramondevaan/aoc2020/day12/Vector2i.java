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

    public Vector2i subtract(Vector2i other) {
        return new Vector2i(this.x - other.x, this.y - other.y);
    }

    public static Vector2i of(int x, int y) {
        return new Vector2i(x, y);
    }
}
