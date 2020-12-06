package nl.ramondevaan.aoc2020.day04;

import lombok.Builder;
import lombok.Value;

import java.time.Year;

@Value
@Builder
public class Passport {
    Year birthYear;
    Year issueYear;
    Year expirationYear;
    String height;
    String hairColor;
    String eyeColor;
    String passportId;
    String countryId;
}
