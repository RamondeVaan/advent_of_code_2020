package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;

import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    private final List<Passport> passports;
    private final PassportNotNullValidator passportNotNullValidator;
    private final PassportValidator passportValidator;

    public Day04(List<String> lines) {
        BlankStringPartitioner partitioner = new BlankStringPartitioner();
        PassportParser parser = new PassportParser();

        List<List<String>> partitionedLines = partitioner.partition(lines);

        passports = partitionedLines.stream().map(parser::parse).collect(Collectors.toList());
        passportNotNullValidator = new PassportNotNullValidator();
        passportValidator = new PassportValidator();
    }

    public long solve1() {
        return passports.stream()
                .filter(passportNotNullValidator::isValid)
                .count();
    }

    public long solve2() {
        return passports.stream()
                .filter(passportValidator::isValid)
                .count();
    }
}
