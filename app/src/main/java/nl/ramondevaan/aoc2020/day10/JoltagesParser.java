package nl.ramondevaan.aoc2020.day10;

import lombok.Value;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class JoltagesParser implements Parser<List<String>, List<Integer>> {

    int range;

    @Override
    public List<Integer> parse(List<String> lines) {
        List<Integer> joltages = lines.stream().map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
        int max = joltages.stream().max(Comparator.naturalOrder()).orElseThrow();
        joltages.add(0);
        joltages.add(max + range);
        joltages.sort(Comparator.naturalOrder());

        return Collections.unmodifiableList(joltages);
    }
}
