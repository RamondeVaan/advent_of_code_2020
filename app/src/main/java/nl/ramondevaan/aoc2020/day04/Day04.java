package nl.ramondevaan.aoc2020.day04;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Day04 {

    private final Pattern heightPattern = Pattern.compile("(\\d+)(in|cm)");
    private final List<Passport> passports;

    public long solve1() {
        return passports.stream()
                .filter(this::isValid1)
                .count();
    }

    private boolean isValid1(Passport passport) {
        return passport.getBirthYear() != null &&
                passport.getIssueYear() != null &&
                passport.getExpirationYear() != null &&
                passport.getHeight() != null &&
                passport.getHairColor() != null &&
                passport.getEyeColor() != null &&
                passport.getPassportId() != null;
    }

    public long solve2() {
        return passports.stream()
                .filter(this::isValid1)
                .filter(this::validBirthYear)
                .filter(this::validIssueYear)
                .filter(this::validExpirationYear)
                .filter(this::validHeight)
                .filter(this::validHairColor)
                .filter(this::validEyeColor)
                .filter(this::validPassportId)
                .count();
    }

    private boolean validBirthYear(Passport passport) {
        int birthYear = Integer.parseInt(passport.getBirthYear());

        return 1920 <= birthYear && birthYear <= 2002;
    }

    private boolean validIssueYear(Passport passport) {
        int issueYear = Integer.parseInt(passport.getIssueYear());

        return 2010 <= issueYear && issueYear <= 2020;
    }

    private boolean validExpirationYear(Passport passport) {
        int expirationYear = Integer.parseInt(passport.getExpirationYear());

        return 2020 <= expirationYear && expirationYear <= 2030;
    }

    private boolean validHeight(Passport passport) {
        Matcher m = heightPattern.matcher(passport.getHeight());

        if(!m.matches()) {
            return false;
        }

        int value = Integer.parseInt(m.group(1));
        String unit = m.group(2);

        if("cm".equals(unit)) {
            return 150 <= value && value <= 193;
        } else if ("in".equals(unit)) {
            return 59 <= value && value <= 76;
        } else {
            return false;
        }
    }

    private boolean validHairColor(Passport passport) {
        return passport.getHairColor().matches("#[0-9a-f]{6}");
    }

    private boolean validEyeColor(Passport passport) {
        return passport.getEyeColor().matches("amb|blu|brn|gry|grn|hzl|oth");
    }

    private boolean validPassportId(Passport passport) {
        return passport.getPassportId().matches("\\d{9}");
    }

    public static Day04Builder builder() {
        return new Day04Builder();
    }

    public static class Day04Builder {
        private final Pattern pattern = Pattern.compile("(\\w+):([^\\s]+)");
        private List<String> lines;

        Day04Builder() {
        }

        public Day04.Day04Builder lines(List<String> lines) {
            this.lines = lines;
            return this;
        }

        private List<Passport> parsePassports() {
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> curr = new HashMap<>();
            list.add(curr);

            for (String line : lines) {
                if (line.isBlank()) {
                    curr = new HashMap<>();
                    list.add(curr);
                }
                curr.putAll(parseLine(line));
            }

            return list.stream().map(this::toPassport).collect(Collectors.toList());
        }

        private Map<String, String> parseLine(String line) {
            Map<String, String> map = new HashMap<>();
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                map.put(matcher.group(1), matcher.group(2));
            }

            return map;
        }

        private Passport toPassport(Map<String, String> map) {
            Passport.PassportBuilder builder = Passport.builder();

            Optional.ofNullable(map.get("byr")).ifPresent(builder::birthYear);
            Optional.ofNullable(map.get("iyr")).ifPresent(builder::issueYear);
            Optional.ofNullable(map.get("eyr")).ifPresent(builder::expirationYear);
            Optional.ofNullable(map.get("hgt")).ifPresent(builder::height);
            Optional.ofNullable(map.get("hcl")).ifPresent(builder::hairColor);
            Optional.ofNullable(map.get("ecl")).ifPresent(builder::eyeColor);
            Optional.ofNullable(map.get("pid")).ifPresent(builder::passportId);
            Optional.ofNullable(map.get("cid")).ifPresent(builder::countryId);

            return builder.build();
        }

        public Day04 build() {
            return new Day04(parsePassports());
        }

        public String toString() {
            return "Day04.Day04Builder(trees=" + this.lines.toString() + ")";
        }
    }
}
