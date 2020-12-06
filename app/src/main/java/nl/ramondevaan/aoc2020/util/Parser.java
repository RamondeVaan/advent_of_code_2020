package nl.ramondevaan.aoc2020.util;

public interface Parser<T, U> {
    U parse(T toParse);
}
