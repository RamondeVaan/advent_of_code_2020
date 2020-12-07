package nl.ramondevaan.aoc2020.util;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtil {
    public static <T> Map<T, Map<T, Long>> deepMapUnmodifiableCopy(Map<T, Map<T, Long>> map) {
        return Map.copyOf(map.keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                entry -> Map.copyOf(map.get(entry)))));
    }

    public static <T> Map<T, Set<T>> deepSetUnmodifiableCopy(Map<T, Set<T>> map) {
        return Map.copyOf(map.keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                entry -> Set.copyOf(map.get(entry)))));
    }
}
