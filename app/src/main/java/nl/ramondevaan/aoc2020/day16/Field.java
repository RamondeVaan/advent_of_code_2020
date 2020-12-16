package nl.ramondevaan.aoc2020.day16;

import lombok.Value;

import java.util.List;

@Value
public class Field {
    public String name;
    public List<Range> ranges;
}
