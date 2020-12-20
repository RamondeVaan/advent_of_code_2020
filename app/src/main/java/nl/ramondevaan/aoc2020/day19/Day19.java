package nl.ramondevaan.aoc2020.day19;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.List;

public class Day19 {

    private final String regex;
    private final List<String> lines;

    public Day19(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        List<List<String>> partitions = partitioner.partition(lines);

        Parser<List<String>, String> parser = new RegexParser();
        regex = parser.parse(partitions.get(0));

        this.lines = partitions.get(1);
    }

    public long solve1() {
        return lines.stream()
                .filter(line -> line.matches(regex))
                .count();
    }

    public long solve2() {
        return 0L;
    }
}
