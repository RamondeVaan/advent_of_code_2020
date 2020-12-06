package nl.ramondevaan.aoc2020.util;

public interface Validator<T> {
    boolean isValid(T toValidate);
}
