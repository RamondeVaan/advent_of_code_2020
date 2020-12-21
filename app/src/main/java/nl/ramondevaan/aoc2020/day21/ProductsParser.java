package nl.ramondevaan.aoc2020.day21;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductsParser implements Parser<List<String>, Products> {

    private final Parser<String, Product> parser;

    public ProductsParser() {
        this.parser = new ProductParser();
    }

    @Override
    public Products parse(List<String> lines) {
        Set<Product> products = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableSet());
        Map<Ingredient, Long> ingredientOccurrences = products.stream().flatMap(product -> product.ingredients.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Allergen, Ingredient> ingredientsByAllergen = computeAllergenToIngredientMap(products);

        return new Products(
                products,
                Collections.unmodifiableMap(ingredientsByAllergen),
                Collections.unmodifiableMap(ingredientOccurrences)
        );
    }

    private Map<Allergen, Ingredient> computeAllergenToIngredientMap(Set<Product> products) {
        Map<Allergen, Set<Ingredient>> potentialIngredientsPerAllergen = getPotentialIngredientsPerAllergen(products);
        Map<Allergen, Ingredient> ingredientsByAllergen = new HashMap<>();

        Map<Allergen, Ingredient> toCheck;

        while (!(toCheck = getSingleValueEntries(potentialIngredientsPerAllergen)).isEmpty()) {
            ingredientsByAllergen.putAll(toCheck);
            for (Ingredient ingredient : toCheck.values()) {
                potentialIngredientsPerAllergen.values()
                        .forEach(potentialIngredients -> potentialIngredients.remove(ingredient));
            }
        }

        return ingredientsByAllergen;
    }

    private Map<Allergen, Set<Ingredient>> getPotentialIngredientsPerAllergen(Set<Product> products) {
        Map<Allergen, Set<Ingredient>> potentialIngredientsPerAllergen = new HashMap<>();

        for (Product product : products) {
            for (Allergen allergen : product.allergens) {
                Set<Ingredient> potentialIngredients = potentialIngredientsPerAllergen.get(allergen);

                if (potentialIngredients == null) {
                    potentialIngredientsPerAllergen.put(allergen, new HashSet<>(product.ingredients));
                } else {
                    potentialIngredients.retainAll(product.ingredients);
                }
            }
        }

        return potentialIngredientsPerAllergen;
    }

    private <T, U> Map<T, U> getSingleValueEntries(Map<T, ? extends Collection<U>> map) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().iterator().next()));
    }
}
