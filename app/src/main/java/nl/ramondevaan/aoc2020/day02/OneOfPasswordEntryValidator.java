package nl.ramondevaan.aoc2020.day02;

import nl.ramondevaan.aoc2020.util.Validator;

import java.util.stream.Stream;

public class OneOfPasswordEntryValidator implements Validator<PasswordEntry> {
    @Override
    public boolean isValid(PasswordEntry toValidate) {
        String password = toValidate.getPassword();
        Policy policy = toValidate.getPolicy();

        return Stream.of(policy.getFirst(), policy.getSecond())
                .map(i -> password.substring(i - 1))
                .filter(str -> str.startsWith(policy.getCombination()))
                .count() == 1L;
    }
}
