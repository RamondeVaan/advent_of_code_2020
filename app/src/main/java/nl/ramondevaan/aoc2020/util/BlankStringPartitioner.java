package nl.ramondevaan.aoc2020.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class BlankStringPartitioner implements Partitioner<String> {
    @Override
    public List<List<String>> partition(List<String> lines) {
        int[] blankLineIndices = IntStream.range(0, lines.size()).filter(index -> lines.get(index).isBlank()).toArray();

        List<List<String>> lists = new ArrayList<>();
        int lastIndex = 0;

        for (int index : blankLineIndices) {
            lists.add(List.copyOf(lines.subList(lastIndex, index)));
            lastIndex = index + 1;
        }

        lists.add(lines.subList(lastIndex, lines.size()));
        return Collections.unmodifiableList(lists);
    }
}
