package nl.ramondevaan.aoc2020.day19;

import nl.ramondevaan.aoc2020.day19.BRule.BRule;
import nl.ramondevaan.aoc2020.day19.BRule.RuleParser;
import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.List;

public class Day19 {

    private final BRule rule;
    private final List<String> lines;

    public Day19(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        List<List<String>> partitions = partitioner.partition(lines);

        Parser<List<String>, BRule> parser = new RuleParser();
        rule = parser.parse(partitions.get(0));

        this.lines = partitions.get(1);
    }

    public long solve1() {
        return lines.stream()
                .filter(line -> rule.take(line, 0).anyMatch(end -> end == line.length()))
                .count();
    }

    public long solve2() {
        return 0L;
    }
}
