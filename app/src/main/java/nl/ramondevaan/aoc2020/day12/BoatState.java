package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class BoatState {
    public Vector2i position;
    public Direction direction;
    public Vector2i relativeWaypoint;
}
