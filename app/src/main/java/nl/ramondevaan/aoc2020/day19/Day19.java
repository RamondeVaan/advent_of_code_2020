package nl.ramondevaan.aoc2020.day19;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.ArrayList;
import java.util.List;

public class Day19 {

    private final Rule rule;
    private final Rule replaced8And11Rule;
    private final List<String> lines;

    public Day19(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        List<List<String>> partitions = partitioner.partition(lines);

        Parser<List<String>, Rule> parser = new RuleParser();
        rule = parser.parse(partitions.get(0));
        replaced8And11Rule = parser.parse(replaceLines(partitions.get(0)));

        this.lines = partitions.get(1);
    }

    private List<String> replaceLines(List<String> lines) {
        List<String> ret = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("8:")) {
                ret.add("8: 42 | 42 8");
            } else if (line.startsWith("11:")) {
                ret.add("11: 42 31 | 42 11 31");
            } else {
                ret.add(line);
            }
        }

        return ret;
    }

    public long solve1() {
        return solve(rule);
    }

    public long solve2() {
        return solve(replaced8And11Rule);
    }

    public long solve(Rule rule) {
        return lines.stream()
                .filter(line -> rule.take(line, 0).anyMatch(end -> end == line.length()))
                .count();
    }
}
