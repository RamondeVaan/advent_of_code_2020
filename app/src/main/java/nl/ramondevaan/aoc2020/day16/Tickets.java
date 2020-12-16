package nl.ramondevaan.aoc2020.day16;

import lombok.Value;

import java.util.List;

@Value
public class Tickets {
    public List<Field> fields;
    public List<Integer> ticket;
    public List<List<Integer>> nearbyTickets;
}
