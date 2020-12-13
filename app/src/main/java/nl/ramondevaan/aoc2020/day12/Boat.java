package nl.ramondevaan.aoc2020.day12;

import lombok.Value;
import lombok.With;

@Value
@With
public class Boat {
    public Vector2i position;
    public Direction direction;
    public Vector2i waypoint;
}
