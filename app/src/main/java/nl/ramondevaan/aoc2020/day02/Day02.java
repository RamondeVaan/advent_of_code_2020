package nl.ramondevaan.aoc2020.day02;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Day02 {

    private final List<PasswordEntry> passwords;

    public long solve1() {
        return passwords.stream()
                .filter(this::isValid1)
                .count();
    }

    private boolean isValid1(PasswordEntry entry) {
        String password = entry.getPassword();
        Policy policy = entry.getPolicy();

        int matches = StringUtils.countMatches(password, policy.getCombination());

        return policy.getFirst() <= matches && matches <= policy.getSecond();
    }

    public long solve2() {
        return passwords.stream()
                .filter(this::isValid2)
                .count();
    }

    private boolean isValid2(PasswordEntry entry) {
        String password = entry.getPassword();
        Policy policy = entry.getPolicy();

        return Stream.of(policy.getFirst(), policy.getSecond())
                .map(i -> password.substring(i - 1))
                .filter(str -> str.startsWith(policy.getCombination()))
                .count() == 1L;
    }

    public static Day02Builder builder() {
        return new Day02Builder();
    }

    public static class Day02Builder {
        private final Pattern passwordEntryPattern = Pattern.compile("(\\d+)-(\\d+)\\s+(\\w+): (\\w+)");
        private List<String> passwords;

        public Day02Builder passwords(List<String> passwords) {
            this.passwords = passwords;
            return this;
        }

        private PasswordEntry parsePasswordEntry(String s) {
            Matcher matcher = passwordEntryPattern.matcher(s);
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

        public Day02 build() {
            return new Day02(passwords.stream().map(this::parsePasswordEntry).collect(Collectors.toList()));
        }

        public String toString() {
            return "Day02.Day02Builder(input=" + this.passwords + ")";
        }
    }
}
