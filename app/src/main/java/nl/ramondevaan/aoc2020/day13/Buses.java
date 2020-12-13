package nl.ramondevaan.aoc2020.day13;

import lombok.Value;

import java.util.Collection;
import java.util.Map;

@Value
public class Buses {
    public int size;
    public Map<Integer, Long> indexToBusIdMap;

    public Collection<Long> busIds() {
        return indexToBusIdMap.values();
    }
}
