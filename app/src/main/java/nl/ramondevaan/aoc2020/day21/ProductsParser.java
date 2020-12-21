package nl.ramondevaan.aoc2020.day21;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
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

        return parseProducts(products);
    }

    private Products parseProducts(Set<Product> products) {
        Set<Ingredient> ingredients = products.stream().flatMap(product -> product.ingredients.stream())
                .collect(Collectors.toUnmodifiableSet());
        Set<Allergen> allergens = products.stream().flatMap(product -> product.allergens.stream())
                .collect(Collectors.toUnmodifiableSet());

        Multimap<Ingredient, Allergen> potentialAllergensPerIngredient = HashMultimap.create();
        ingredients.forEach(ingredient -> potentialAllergensPerIngredient.putAll(ingredient, allergens));

        Multimap<Allergen, Ingredient> potentialIngredientsPerAllergen = HashMultimap.create();
        allergens.forEach(allergen -> potentialIngredientsPerAllergen.putAll(allergen, ingredients));

        Map<Allergen, Ingredient> allergenToIngredientMap = new HashMap<>();

        for (Product product : products) {
            for (Allergen allergen : product.allergens) {
                Set<Ingredient> potentialIngredients = new HashSet<>(potentialIngredientsPerAllergen.get(allergen));

                Sets.SetView<Ingredient> difference = Sets.difference(potentialIngredients, product.ingredients);

                for (Ingredient ingredient : difference) {
                    potentialIngredientsPerAllergen.remove(allergen, ingredient);
                    potentialAllergensPerIngredient.remove(ingredient, allergen);
                }

                Collection<Ingredient> result = potentialIngredientsPerAllergen.get(allergen);
                if (result.size() == 1) {
                    result.forEach(ingredient -> allergenToIngredientMap.put(allergen, ingredient));
                }
            }
        }

        Map<Allergen, Ingredient> toCheck = new HashMap<>(allergenToIngredientMap);
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
                            allergenToIngredientMap.put(allergenToRemove, setIngredient);
                            next.put(allergenToRemove, setIngredient);
                        }
                    }
                }
            }
            toCheck = next;
        }

        Map<Ingredient, Long> ingredientOccurrences = products.stream()
                .flatMap(product -> product.ingredients.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new Products(
                ingredients,
                allergens,
                products,
                allergenToIngredientMap,
                ingredientOccurrences
        );
    }
}
