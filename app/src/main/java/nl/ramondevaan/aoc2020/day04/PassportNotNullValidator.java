package nl.ramondevaan.aoc2020.day04;

import nl.ramondevaan.aoc2020.util.Validator;

public class PassportNotNullValidator implements Validator<Passport> {
    @Override
    public boolean isValid(Passport toValidate) {
        return toValidate.getBirthYear() != null &&
                toValidate.getIssueYear() != null &&
                toValidate.getExpirationYear() != null &&
                toValidate.getHeight() != null &&
                toValidate.getHairColor() != null &&
                toValidate.getEyeColor() != null &&
                toValidate.getPassportId() != null;
    }
}
