package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassportValidator implements Validator<Passport> {

    private static final Pattern heightPattern = Pattern.compile("(\\d+)(in|cm)");

    private final PassportNotNullValidator notNullValidator;

    public PassportValidator() {
        this.notNullValidator = new PassportNotNullValidator();
    }

    @Override
    public boolean isValid(Passport toValidate) {
        return notNullValidator.isValid(toValidate) &&
                validBirthYear(toValidate) &&
                validIssueYear(toValidate) &&
                validExpirationYear(toValidate) &&
                validHeight(toValidate) &&
                validHairColor(toValidate) &&
                validEyeColor(toValidate) &&
                validPassportId(toValidate);
    }

    private boolean validBirthYear(Passport passport) {
        int birthYear = passport.getBirthYear().getValue();

        return 1920 <= birthYear && birthYear <= 2002;
    }

    private boolean validIssueYear(Passport passport) {
        int issueYear = passport.getIssueYear().getValue();

        return 2010 <= issueYear && issueYear <= 2020;
    }

    private boolean validExpirationYear(Passport passport) {
        int expirationYear = passport.getExpirationYear().getValue();

        return 2020 <= expirationYear && expirationYear <= 2030;
    }

    private boolean validHeight(Passport passport) {
        Matcher m = heightPattern.matcher(passport.getHeight());

        if (!m.matches()) {
            return false;
        }

        int value = Integer.parseInt(m.group(1));
        String unit = m.group(2);

        if ("cm".equals(unit)) {
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
}
