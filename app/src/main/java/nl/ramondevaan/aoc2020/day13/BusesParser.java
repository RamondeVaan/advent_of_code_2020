package nl.ramondevaan.aoc2020.day13;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusesParser implements Parser<String, Buses> {

    @Override
    public Buses parse(String toParse) {
        String[] split = toParse.split(",");

        int size = split.length;
        Map<Integer, Long> indexToBusIdMap = IntStream.range(0, size)
                .filter(index -> !split[index].equals("x"))
                .boxed()
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        index -> Long.parseLong(split[index])));

        return new Buses(size, indexToBusIdMap);
    }
}
