package nl.ramondevaan.aoc2020.day21;

import com.google.common.collect.Sets;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    private final Products products;

    public Day21(List<String> lines) {
        Parser<List<String>, Products> parser = new ProductsParser();
        products = parser.parse(lines);
    }

    public long solve1() {
        Set<Ingredient> ingredientsWithAllergens = new HashSet<>(products.ingredientsByAllergen.values());

        return Sets.difference(products.ingredients, ingredientsWithAllergens).stream()
                .mapToLong(products.ingredientOccurrences::get)
                .sum();
    }

    public String solve2() {
        return products.ingredientsByAllergen.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Allergen::getName)))
                .map(Map.Entry::getValue)
                .map(Ingredient::getName)
                .collect(Collectors.joining(","));
    }
}
