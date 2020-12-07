package nl.ramondevaan.aoc2020.day07;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BagParser implements Parser<List<String>, Bags> {
    private static final String COLOR_REGEX = ".*?";
    private static final String BAG_COLOR_REGEX = "(" + COLOR_REGEX + ") bags contain";
    private static final String BAG_COLOR_AND_COUNT_REGEX = "(\\d+) (" + COLOR_REGEX + ") bag";

    private static final Pattern BAG_COLOR_PATTERN = Pattern.compile(BAG_COLOR_REGEX);
    private static final Pattern BAG_COLOR_AND_COUNT_PATTERN = Pattern.compile(BAG_COLOR_AND_COUNT_REGEX);

    @Override
    public Bags parse(List<String> lines) {
        Bags.BagsBuilder builder = Bags.builder();
        Map<String, Bag> bagMap = new HashMap<>();
        List<ImmutablePair<Bag, Integer>> bagAndMatcherEnds = new ArrayList<>();

        for (String line : lines) {
            ImmutablePair<Bag, Integer> bagAndMatcherEnd = bagAndMatcherEnd(line);
            bagAndMatcherEnds.add(bagAndMatcherEnd);
            builder.addBag(bagAndMatcherEnd.left);
            bagMap.put(bagAndMatcherEnd.left.getColor(), bagAndMatcherEnd.left);
        }

        for (int i = 0; i < lines.size(); i++) {
            ImmutablePair<Bag, Integer> bagAndMatcherEnd = bagAndMatcherEnds.get(i);
            String line = lines.get(i);
            Map<String, Long> children = children(line, bagAndMatcherEnd.right);
            children.forEach((key, value) -> {
                Bag child = bagMap.get(key);
                builder.addBagToBag(bagAndMatcherEnd.left, child, value);
            });
        }

        return builder.build();
    }

    private ImmutablePair<Bag, Integer> bagAndMatcherEnd(String line) {
        Matcher bagColorMatcher = BAG_COLOR_PATTERN.matcher(line);

        if (!bagColorMatcher.find()) {
            throw new IllegalStateException();
        }

        String color = bagColorMatcher.group(1);
        int lastIndex = bagColorMatcher.end();

        return new ImmutablePair<>(new Bag(color), lastIndex);
    }

    private Map<String, Long> children(String line, int fromIndex) {
        Map<String, Long> children = new HashMap<>();

        int lastIndex = fromIndex;

        Matcher bagColorAndCountMatcher = BAG_COLOR_AND_COUNT_PATTERN.matcher(line);

        while (bagColorAndCountMatcher.find(lastIndex)) {
            long childCardinality = Long.parseLong(bagColorAndCountMatcher.group(1));
            String childColor = bagColorAndCountMatcher.group(2);

            children.put(childColor, childCardinality);

            lastIndex = bagColorAndCountMatcher.end();
        }

        return children;
    }
}
