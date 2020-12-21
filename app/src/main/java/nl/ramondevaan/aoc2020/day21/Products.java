package nl.ramondevaan.aoc2020.day21;

import lombok.Value;

import java.util.Map;
import java.util.Set;

@Value
public class Products {
    public Set<Ingredient> ingredients;
    public Set<Allergen> allergens;
    public Set<Product> products;
    public Map<Allergen, Ingredient> ingredientsByAllergen;
    public Map<Ingredient, Long> ingredientOccurrences;
}
