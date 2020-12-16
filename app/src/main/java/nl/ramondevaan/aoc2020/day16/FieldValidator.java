package nl.ramondevaan.aoc2020.day16;

public class FieldValidator {
    public boolean isValid(Field field, int value) {
        return field.ranges.stream().anyMatch(range -> range.start <= value && value <= range.end);
    }
}
