package nl.ramondevaan.aoc2020.day07;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static nl.ramondevaan.aoc2020.util.CollectionUtil.deepMapUnmodifiableCopy;
import static nl.ramondevaan.aoc2020.util.CollectionUtil.deepSetUnmodifiableCopy;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Bags {
    private final Map<String, Bag> bagsByColor;
    private final Map<Bag, Set<Bag>> parentsByBag;
    private final Map<Bag, Map<Bag, Long>> childrenByBag;

    public Collection<Bag> bags() {
        return bagsByColor.values();
    }

    public Bag bagByColor(String name) {
        return bagsByColor.get(name);
    }

    public Map<Bag, Long> children(Bag bag) {
        return childrenByBag.get(bag);
    }

    public Set<Bag> parents(Bag bag) {
        return parentsByBag.get(bag);
    }

    public static BagsBuilder builder() {
        return new BagsBuilder();
    }

    public static class BagsBuilder {
        private final Map<String, Bag> bagsByColor;
        private final Map<Bag, Set<Bag>> parentsByBag;
        private final Map<Bag, Map<Bag, Long>> childrenByBag;

        private BagsBuilder() {
            bagsByColor = new HashMap<>();
            parentsByBag = new HashMap<>();
            childrenByBag = new HashMap<>();
        }

        BagsBuilder addBagToBag(Bag from, Bag to, long cardinality) {
            parentsByBag.get(to).add(from);
            childrenByBag.get(from).put(to, cardinality);

            return this;
        }

        BagsBuilder addBag(Bag bag) {
            if (bagsByColor.containsValue(bag)) {
                throw new IllegalArgumentException();
            }
            bagsByColor.put(bag.getColor(), bag);
            parentsByBag.put(bag, new HashSet<>());
            childrenByBag.put(bag, new HashMap<>());

            return this;
        }

        public Bags build() {
            return new Bags(
                    Map.copyOf(bagsByColor),
                    deepSetUnmodifiableCopy(parentsByBag),
                    deepMapUnmodifiableCopy(childrenByBag)
            );
        }
    }
}
