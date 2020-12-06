package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassportParser implements Parser<List<String>, Passport> {
    private static final Pattern pairPattern = Pattern.compile("(\\w+):([^\\s]+)");

    private final YearParser yearParser;

    public PassportParser() {
        yearParser = new YearParser();
    }

    @Override
    public Passport parse(List<String> lines) {
        Map<String, String> map = new HashMap<>();

        for (String line : lines) {
            Matcher matcher = pairPattern.matcher(line);

            while (matcher.find()) {
                map.put(matcher.group(1), matcher.group(2));
            }
        }

        Passport.PassportBuilder builder = Passport.builder();

        Optional.ofNullable(map.get("byr")).map(yearParser::parse).ifPresent(builder::birthYear);
        Optional.ofNullable(map.get("iyr")).map(yearParser::parse).ifPresent(builder::issueYear);
        Optional.ofNullable(map.get("eyr")).map(yearParser::parse).ifPresent(builder::expirationYear);
        Optional.ofNullable(map.get("hgt")).ifPresent(builder::height);
        Optional.ofNullable(map.get("hcl")).ifPresent(builder::hairColor);
        Optional.ofNullable(map.get("ecl")).ifPresent(builder::eyeColor);
        Optional.ofNullable(map.get("pid")).ifPresent(builder::passportId);
        Optional.ofNullable(map.get("cid")).ifPresent(builder::countryId);

        return builder.build();
    }
}
