package nl.ramondevaan.aoc2020.day19;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;

public class Day19 {

    private final String regex;

    public Day19(List<String> lines) {
        Parser<List<String>, String> parser = new RegexParser();

        regex = parser.parse(lines);
    }

    public long solve1() {
        return 0L;
    }

    public long solve2() {
        return 0L;
    }
}
