package nl.ramondevaan.aoc2020.day13;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Day13 {

    private final long arrivalTime;
    private final Map<Integer, Long> indexToBusIdMap;

    public Day13(List<String> lines) {
        arrivalTime = Long.parseLong(lines.get(0));

        Parser<String, Map<Integer, Long>> parser = new BusIdParser();
        indexToBusIdMap = parser.parse(lines.get(1));
    }

    public long solve1() {
        long arrivalTimeMinus1 = arrivalTime - 1L;

        return indexToBusIdMap.values().stream()
                .map(busIds -> ImmutablePair.of(busIds, busIds - (arrivalTimeMinus1 % busIds) - 1L))
                .min(Comparator.comparingLong(pair -> pair.right))
                .map(pair -> pair.left * pair.right)
                .orElseThrow();
    }

    public long solve2() {
        long time = 0;
        long step = 1;

        for (Map.Entry<Integer, Long> entry : indexToBusIdMap.entrySet()) {
            for (long t = time; true; t += step) {
                if ((t + entry.getKey()) % entry.getValue() == 0) {
                    time = t;
                    step *= entry.getValue();
                    break;
                }
            }
        }

        return time;
    }
}
