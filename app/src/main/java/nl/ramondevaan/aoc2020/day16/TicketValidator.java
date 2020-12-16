package nl.ramondevaan.aoc2020.day16;

import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class TicketValidator {

    FieldValidator fieldValidator;
    List<Field> fields;

    public Set<Integer> invalidValues(List<Integer> toValidate) {
        return toValidate.stream()
                .filter(value -> fields.stream().noneMatch(field -> fieldValidator.isValid(field, value)))
                .collect(Collectors.toUnmodifiableSet());
    }
}
