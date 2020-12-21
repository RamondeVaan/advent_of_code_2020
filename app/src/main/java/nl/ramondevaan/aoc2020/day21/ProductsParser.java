package nl.ramondevaan.aoc2020.day21;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsParser implements Parser<List<String>, Products> {

    private final Parser<String, Product> parser;

    public ProductsParser() {
        this.parser = new ProductParser();
    }

    @Override
    public Products parse(List<String> lines) {
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Allergen> allergens = new HashSet<>();
        Map<Ingredient, Long> ingredientOccurrences = new HashMap<>();
        Set<Product> products = new HashSet<>();
        Multimap<Ingredient, Allergen> potentialAllergensPerIngredient = HashMultimap.create();
        Multimap<Allergen, Ingredient> potentialIngredientsPerAllergen = HashMultimap.create();
        Map<Allergen, Ingredient> setIngredientsByAllergen = new HashMap<>();

        for (String line : lines) {
            Product product = parser.parse(line);
            products.add(product);

            ingredients.addAll(product.ingredients);
            allergens.addAll(product.allergens);

            product.ingredients.forEach(ingredient -> ingredientOccurrences.merge(ingredient, 1L, Long::sum));

            for (Allergen allergen : product.allergens) {
                Collection<Ingredient> potentialIngredients = potentialIngredientsPerAllergen.get(allergen);

                if (potentialIngredients.isEmpty()) {
                    potentialIngredientsPerAllergen.putAll(allergen, product.ingredients);
                    product.ingredients.forEach(ingredient ->
                            potentialAllergensPerIngredient.put(ingredient, allergen));
                } else {
                    Set<Ingredient> difference = potentialIngredients.stream()
                            .filter(ingredient -> !product.ingredients.contains(ingredient))
                            .collect(Collectors.toSet());
                    difference.forEach(ingredient -> potentialAllergensPerIngredient.remove(ingredient, allergen));
                    potentialIngredients.removeAll(difference);
                    potentialIngredients.retainAll(product.ingredients);
                    if (potentialIngredients.size() == 1) {
                        setIngredientsByAllergen.put(allergen, potentialIngredients.iterator().next());
                    }
                }
            }
        }

        Map<Allergen, Ingredient> ingredientsByAllergen = reduce(potentialIngredientsPerAllergen,
                potentialAllergensPerIngredient, setIngredientsByAllergen);

        return new Products(
                Collections.unmodifiableSet(ingredients),
                Collections.unmodifiableSet(allergens),
                Collections.unmodifiableSet(products),
                Collections.unmodifiableMap(ingredientsByAllergen),
                Collections.unmodifiableMap(ingredientOccurrences)
        );
    }

    private Map<Allergen, Ingredient> reduce(Multimap<Allergen, Ingredient> potentialIngredientsPerAllergen,
                                             Multimap<Ingredient, Allergen> potentialAllergensPerIngredient,
                                             Map<Allergen, Ingredient> initial) {
        Map<Allergen, Ingredient> ingredientsByAllergen = new HashMap<>(initial);

        Map<Allergen, Ingredient> toCheck = new HashMap<>(initial);
        Map<Allergen, Ingredient> next;

        while (!toCheck.isEmpty()) {
            next = new HashMap<>();
            for (Map.Entry<Allergen, Ingredient> entry : toCheck.entrySet()) {
                Allergen allergen = entry.getKey();
                Ingredient ingredient = entry.getValue();

                Set<Allergen> potentialAllergens = new HashSet<>(potentialAllergensPerIngredient.get(ingredient));
                Set<Allergen> difference = Sets.difference(potentialAllergens, Set.of(allergen));

                for (Allergen allergenToRemove : difference) {
                    potentialAllergensPerIngredient.remove(ingredient, allergenToRemove);
                    potentialIngredientsPerAllergen.remove(allergenToRemove, ingredient);

                    Collection<Ingredient> result = potentialIngredientsPerAllergen.get(allergenToRemove);
                    if (result.size() == 1) {
                        for (Ingredient setIngredient : result) {
                            ingredientsByAllergen.put(allergenToRemove, setIngredient);
                            next.put(allergenToRemove, setIngredient);
                        }
                    }
                }
            }
            toCheck = next;
        }

        return ingredientsByAllergen;
    }
}
