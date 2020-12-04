package nl.ramondevaan.aoc2020.day04;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Passport {
    String birthYear;
    String issueYear;
    String expirationYear;
    String height;
    String hairColor;
    String eyeColor;
    String passportId;
    String countryId;
}
