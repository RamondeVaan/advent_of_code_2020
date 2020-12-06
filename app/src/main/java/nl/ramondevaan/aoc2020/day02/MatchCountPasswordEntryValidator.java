package nl.ramondevaan.aoc2020.day02;

import nl.ramondevaan.aoc2020.util.Validator;
import org.apache.commons.lang3.StringUtils;

public class MatchCountPasswordEntryValidator implements Validator<PasswordEntry> {

    @Override
    public boolean isValid(PasswordEntry toValidate) {
        String password = toValidate.getPassword();
        Policy policy = toValidate.getPolicy();

        int matches = StringUtils.countMatches(password, policy.getCombination());

        return policy.getFirst() <= matches && matches <= policy.getSecond();
    }
}
