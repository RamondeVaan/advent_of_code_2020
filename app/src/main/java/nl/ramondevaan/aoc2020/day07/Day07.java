package nl.ramondevaan.aoc2020.day07;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 {

    private final Bags bags;

    public Day07(List<String> lines) {
        BagParser parser = new BagParser();
        bags = parser.parse(lines);
    }

    public long solve1() {
        Bag shinyGoldBag = bags.bagByColor("shiny gold");

        Set<Bag> parents = new HashSet<>(bags.parents(shinyGoldBag));
        Set<Bag> toCheck = new HashSet<>(bags.parents(shinyGoldBag));
        Set<Bag> nextToCheck = new HashSet<>();

        while (!toCheck.isEmpty()) {

            for (Bag bag : toCheck) {
                for (Bag parent : bags.parents(bag)) {
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
        Bag shinyGoldBag = bags.bagByColor("shiny gold");
        Map<Bag, Long> bagsContainedMap = new HashMap<>();

        Set<Bag> bagsToCheck = bags.bags().stream()
                .filter(bag -> bags.children(bag).isEmpty())
                .collect(Collectors.toUnmodifiableSet());

        while (!bagsToCheck.isEmpty()) {
            for (Bag bag : bagsToCheck) {
                long bagsContained = bagsContained(bagsContainedMap, bag);
                bagsContainedMap.put(bag, bagsContained);
            }

            bagsToCheck = bagsToCheck.stream()
                    .flatMap(bag -> bags.parents(bag).stream())
                    .distinct()
                    .filter(bag -> bagsContainedMap.keySet().containsAll(bags.children(bag).keySet()))
                    .collect(Collectors.toUnmodifiableSet());

            if (bagsToCheck.contains(shinyGoldBag)) {
                break;
            }
        }

        return bagsContained(bagsContainedMap, shinyGoldBag);
    }

    private long bagsContained(Map<Bag, Long> bagsContainedMap, Bag bag) {
        return bags.children(bag).entrySet().stream()
                .mapToLong(entry -> (bagsContainedMap.get(entry.getKey()) + 1) * entry.getValue())
                .sum();
    }
}
