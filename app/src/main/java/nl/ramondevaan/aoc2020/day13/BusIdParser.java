package nl.ramondevaan.aoc2020.day13;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusIdParser implements Parser<String, Map<Integer, Long>> {

    @Override
    public Map<Integer, Long> parse(String toParse) {
        String[] split = toParse.split(",");
        return IntStream.range(0, split.length)
                .filter(index -> !split[index].equals("x"))
                .boxed()
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        index -> Long.parseLong(split[index])));
    }
}
