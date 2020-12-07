package nl.ramondevaan.aoc2020.day07;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BagParser implements Parser<List<String>, Map<String, Bag>> {
    private static final String COLOR_REGEX = ".*?";
    private static final String BAG_COLOR_REGEX = "(" + COLOR_REGEX + ") bags contain";
    private static final String BAG_COLOR_AND_COUNT_REGEX = "(\\d+) (" + COLOR_REGEX + ") bag";

    private static final Pattern BAG_COLOR_PATTERN = Pattern.compile(BAG_COLOR_REGEX);
    private static final Pattern BAG_COLOR_AND_COUNT_PATTERN = Pattern.compile(BAG_COLOR_AND_COUNT_REGEX);

    @Override
    public Map<String, Bag> parse(List<String> lines) {
        Map<String, Map<String, Integer>> bagTree = bagTree(lines);
        Map<String, Bag> bagMap = new HashMap<>();
        Map<Bag, Map<String, Integer>> childrenMap = new HashMap<>();

        bagTree.forEach((color, map) -> {
            Bag bag = new Bag();
            bag.setParents(new HashSet<>());
            bag.setBags(new HashMap<>());
            bag.setColor(color);
            bagMap.put(color, bag);
            childrenMap.put(bag, map);
        });
        childrenMap.forEach((bag, childMap) -> childMap.forEach((color, cardinality) -> {
            Bag childBag = bagMap.get(color);
            childBag.getParents().add(bag);
            bag.getBags().put(childBag, cardinality);
        }));

        return bagMap;
    }

    private Map<String, Map<String, Integer>> bagTree(List<String> lines) {
        Map<String, Map<String, Integer>> bags = new HashMap<>();

        for (String line : lines) {
            ImmutablePair<String, Integer> colorAndMatcherEnd = colorAndMatcherEnd(line);
            Map<String, Integer> children = children(line, colorAndMatcherEnd.right);
            bags.put(colorAndMatcherEnd.left, children);
        }

        return bags;
    }

    private ImmutablePair<String, Integer> colorAndMatcherEnd(String line) {
        Matcher bagColorMatcher = BAG_COLOR_PATTERN.matcher(line);

        if (!bagColorMatcher.find()) {
            throw new IllegalStateException();
        }

        String color = bagColorMatcher.group(1);
        int lastIndex = bagColorMatcher.end();

        return new ImmutablePair<>(color, lastIndex);
    }

    private Map<String, Integer> children(String line, int fromIndex) {
        Map<String, Integer> children = new HashMap<>();

        int lastIndex = fromIndex;

        Matcher bagColorAndCountMatcher = BAG_COLOR_AND_COUNT_PATTERN.matcher(line);

        while (bagColorAndCountMatcher.find(lastIndex)) {
            int childCardinality = Integer.parseInt(bagColorAndCountMatcher.group(1));
            String childColor = bagColorAndCountMatcher.group(2);

            children.put(childColor, childCardinality);

            lastIndex = bagColorAndCountMatcher.end();
        }

        return children;
    }
}
