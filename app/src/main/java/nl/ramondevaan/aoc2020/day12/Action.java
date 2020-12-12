package nl.ramondevaan.aoc2020.day12;

import lombok.Value;

@Value
public class Action {
    public ActionType actionType;
    public int value;
}
