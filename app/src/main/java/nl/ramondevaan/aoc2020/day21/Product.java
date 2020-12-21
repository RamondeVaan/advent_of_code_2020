package nl.ramondevaan.aoc2020.day21;

import lombok.Value;

import java.util.Set;

@Value
public class Product {
    public Set<Ingredient> ingredients;
    public Set<Allergen> allergens;
}
