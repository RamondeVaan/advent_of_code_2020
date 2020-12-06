package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.Parser;

import java.time.Year;

public class YearParser implements Parser<String, Year> {
    @Override
    public Year parse(String toParse) {
        return Year.parse(toParse);
    }
}
