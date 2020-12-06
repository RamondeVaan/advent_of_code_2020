package nl.ramondevaan.aoc2020.day02;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordEntryParser implements Parser<String, PasswordEntry> {
    private static final Pattern passwordEntryPattern = Pattern.compile("(\\d+)-(\\d+)\\s+(\\w+): (\\w+)");

    @Override
    public PasswordEntry parse(String toParse) {
        Matcher matcher = passwordEntryPattern.matcher(toParse);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        Policy policy = new Policy(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                matcher.group(3)
        );
        return new PasswordEntry(policy, matcher.group(4));
    }
}
