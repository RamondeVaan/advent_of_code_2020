package nl.ramondevaan.aoc2020.day07;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 {

    private final Map<String, Bag> bagMap;

    public Day07(List<String> lines) {
        BagParser parser = new BagParser();
        bagMap = parser.parse(lines);
    }

    public long solve1() {
        Bag shinyGoldBag = bagMap.get("shiny gold");

        Set<Bag> parents = new HashSet<>(shinyGoldBag.getParents());
        Set<Bag> toCheck = new HashSet<>(shinyGoldBag.getParents());
        Set<Bag> nextToCheck = new HashSet<>();

        while (!toCheck.isEmpty()) {

            for (Bag bag : toCheck) {
                for (Bag parent : bag.getParents()) {
                    if (parents.add(parent)) {
                        nextToCheck.add(parent);
                    }
                }
            }

            toCheck.clear();
            toCheck.addAll(nextToCheck);
            nextToCheck.clear();
        }

        return parents.size();
    }

    public long solve2() {
        Bag shinyGoldBag = bagMap.get("shiny gold");
        Map<Bag, Long> bagsContainedMap = new HashMap<>();

        Set<Bag> bagsToCheck = bagMap.values().stream()
                .filter(bag -> bag.getBags().isEmpty())
                .collect(Collectors.toSet());

        while (!bagsToCheck.isEmpty()) {
            for(Bag bag : bagsToCheck) {
                long bagsContained = bagsContained(bagsContainedMap, bag);
                bagsContainedMap.put(bag, bagsContained);
            }

            bagsToCheck = bagsToCheck.stream()
                    .flatMap(bag -> bag.getParents().stream())
                    .distinct()
                    .filter(bag -> bagsContainedMap.keySet().containsAll(bag.getBags().keySet()))
                    .collect(Collectors.toSet());

            if(bagsToCheck.contains(shinyGoldBag)) {
                break;
            }
        }

        return bagsContained(bagsContainedMap, shinyGoldBag);
    }

    private static long bagsContained(Map<Bag, Long> bagsContainedMap, Bag bag) {
        return bag.getBags().entrySet().stream()
                .mapToLong(entry -> (bagsContainedMap.get(entry.getKey()) + 1) * entry.getValue())
                .sum();
    }
}
