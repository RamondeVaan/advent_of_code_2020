package nl.ramondevaan.aoc2020.day21;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductParser implements Parser<String, Product> {
    @Override
    public Product parse(String toParse) {
        int index = toParse.indexOf('(');

        String before = toParse.substring(0, index - 1);
        String after = toParse.substring(index + 10, toParse.length() - 1);

        Set<String> ingredients = Arrays.stream(before.split(" ")).collect(Collectors.toUnmodifiableSet());
        Set<String> allergens = Arrays.stream(after.split(", ")).collect(Collectors.toUnmodifiableSet());

        return new Product(ingredients, allergens);
    }
}
