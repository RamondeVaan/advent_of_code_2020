package nl.ramondevaan.aoc2020.day21;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day21 {

    private final List<Product> products;

    public Day21(List<String> lines) {
        Parser<String, Product> parser = new ProductParser();
        products = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        Set<String> ingredients = products.stream().flatMap(product -> product.ingredients.stream())
                .collect(Collectors.toUnmodifiableSet());
        Set<String> allergens = products.stream().flatMap(product -> product.allergens.stream())
                .collect(Collectors.toUnmodifiableSet());

        Multimap<String, String> potentialIngredientsPerAllergen = getIngredientPerAllergen(ingredients, allergens);
        Set<String> ingredientsThatMayHaveAnAllergen = new HashSet<>(potentialIngredientsPerAllergen.values());

        Map<String, Long> productOccurences = products.stream()
                .flatMap(product -> product.ingredients.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return Sets.difference(ingredients, ingredientsThatMayHaveAnAllergen).stream()
                .mapToLong(productOccurences::get)
                .sum();
    }

    private Multimap<String, String> getIngredientPerAllergen(Set<String> ingredients, Set<String> allergens) {

        Multimap<String, String> potentialAllergensPerIngredient = HashMultimap.create();
        ingredients.forEach(ingredient -> potentialAllergensPerIngredient.putAll(ingredient, allergens));

        Multimap<String, String> potentialIngredientsPerAllergen = HashMultimap.create();
        allergens.forEach(allergen -> potentialIngredientsPerAllergen.putAll(allergen, ingredients));

        Map<String, String> allergenToIngredientMap = new HashMap<>();

        for (Product product : products) {
            for (String allergen : product.allergens) {
                Set<String> potentialIngredients = new HashSet<>(potentialIngredientsPerAllergen.get(allergen));

                Sets.SetView<String> difference = Sets.difference(potentialIngredients, product.ingredients);

                for (String ingredient : difference) {
                    potentialIngredientsPerAllergen.remove(allergen, ingredient);
                    potentialAllergensPerIngredient.remove(ingredient, allergen);
                }

                Collection<String> result = potentialIngredientsPerAllergen.get(allergen);
                if (result.size() == 1) {
                    result.forEach(ingredient -> allergenToIngredientMap.put(allergen, ingredient));
                }
            }
        }

        Map<String, String> toCheck = new HashMap<>(allergenToIngredientMap);
        Map<String, String> next;

        while (!toCheck.isEmpty()) {
            next = new HashMap<>();
            for (Map.Entry<String, String> entry : toCheck.entrySet()) {
                String allergen = entry.getKey();
                String ingredient = entry.getValue();

                Set<String> potentialAllergens = new HashSet<>(potentialAllergensPerIngredient.get(ingredient));
                Set<String> difference = Sets.difference(potentialAllergens, Set.of(allergen));

                for (String allergenToRemove : difference) {
                    potentialAllergensPerIngredient.remove(ingredient, allergenToRemove);
                    potentialIngredientsPerAllergen.remove(allergenToRemove, ingredient);

                    Collection<String> result = potentialIngredientsPerAllergen.get(allergenToRemove);
                    if (result.size() == 1) {
                        for (String setIngredient : result) {
                            allergenToIngredientMap.put(allergenToRemove, setIngredient);
                            next.put(allergenToRemove, setIngredient);
                        }
                    }
                }
            }
            toCheck = next;
        }

        return potentialIngredientsPerAllergen;
    }

    private Map<String, String> aa(Set<String> ingredients, Set<String> allergens) {

        Multimap<String, String> potentialAllergensPerIngredient = HashMultimap.create();
        ingredients.forEach(ingredient -> potentialAllergensPerIngredient.putAll(ingredient, allergens));

        Multimap<String, String> potentialIngredientsPerAllergen = HashMultimap.create();
        allergens.forEach(allergen -> potentialIngredientsPerAllergen.putAll(allergen, ingredients));

        Map<String, String> allergenToIngredientMap = new HashMap<>();

        for (Product product : products) {
            for (String allergen : product.allergens) {
                Set<String> potentialIngredients = new HashSet<>(potentialIngredientsPerAllergen.get(allergen));

                Sets.SetView<String> difference = Sets.difference(potentialIngredients, product.ingredients);

                for (String ingredient : difference) {
                    potentialIngredientsPerAllergen.remove(allergen, ingredient);
                    potentialAllergensPerIngredient.remove(ingredient, allergen);
                }

                Collection<String> result = potentialIngredientsPerAllergen.get(allergen);
                if (result.size() == 1) {
                    result.forEach(ingredient -> allergenToIngredientMap.put(allergen, ingredient));
                }
            }
        }

        Map<String, String> toCheck = new HashMap<>(allergenToIngredientMap);
        Map<String, String> next;

        while (!toCheck.isEmpty()) {
            next = new HashMap<>();
            for (Map.Entry<String, String> entry : toCheck.entrySet()) {
                String allergen = entry.getKey();
                String ingredient = entry.getValue();

                Set<String> potentialAllergens = new HashSet<>(potentialAllergensPerIngredient.get(ingredient));
                Set<String> difference = Sets.difference(potentialAllergens, Set.of(allergen));

                for (String allergenToRemove : difference) {
                    potentialAllergensPerIngredient.remove(ingredient, allergenToRemove);
                    potentialIngredientsPerAllergen.remove(allergenToRemove, ingredient);

                    Collection<String> result = potentialIngredientsPerAllergen.get(allergenToRemove);
                    if (result.size() == 1) {
                        for (String setIngredient : result) {
                            allergenToIngredientMap.put(allergenToRemove, setIngredient);
                            next.put(allergenToRemove, setIngredient);
                        }
                    }
                }
            }
            toCheck = next;
        }

        return allergenToIngredientMap;
    }

    public String solve2() {
        Set<String> ingredients = products.stream().flatMap(product -> product.ingredients.stream())
                .collect(Collectors.toUnmodifiableSet());
        Set<String> allergens = products.stream().flatMap(product -> product.allergens.stream())
                .collect(Collectors.toUnmodifiableSet());

        Map<String, String> allergenToIngredientMap = aa(ingredients, allergens);

        return allergenToIngredientMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue)
                .collect(Collectors.joining(","));
    }
}
