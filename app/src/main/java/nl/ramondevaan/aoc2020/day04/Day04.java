package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;
import nl.ramondevaan.aoc2020.util.Validator;

import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    private final List<Passport> passports;
    private final Validator<Passport> passportNotNullValidator;
    private final Validator<Passport> passportValidator;

    public Day04(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        Parser<List<String>, Passport> parser = new PassportParser();

        List<List<String>> partitionedLines = partitioner.partition(lines);

        passports = partitionedLines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
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
