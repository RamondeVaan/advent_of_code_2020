package nl.ramondevaan.aoc2020.day02;

import java.util.List;
import java.util.stream.Collectors;

public class Day02 {

    private final List<PasswordEntry> passwords;
    private final MatchCountPasswordEntryValidator matchCountPasswordEntryValidator;
    private final OneOfPasswordEntryValidator oneOfPasswordEntryValidator;

    public Day02(List<String> lines) {
        PasswordEntryParser parser = new PasswordEntryParser();
        passwords = lines.stream().map(parser::parse).collect(Collectors.toList());

        matchCountPasswordEntryValidator = new MatchCountPasswordEntryValidator();
        oneOfPasswordEntryValidator = new OneOfPasswordEntryValidator();
    }

    public long solve1() {
        return passwords.stream()
                .filter(matchCountPasswordEntryValidator::isValid)
                .count();
    }

    public long solve2() {
        return passwords.stream()
                .filter(oneOfPasswordEntryValidator::isValid)
                .count();
    }
}
