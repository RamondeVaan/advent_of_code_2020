package nl.ramondevaan.aoc2020.day16;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {

    private final Tickets tickets;
    private final FieldValidator fieldValidator;
    private final TicketValidator validator;

    public Day16(List<String> lines) {
        Parser<List<String>, Tickets> parser = new TicketsParser();
        tickets = parser.parse(lines);
        fieldValidator = new FieldValidator();
        validator = new TicketValidator(fieldValidator, tickets.fields);
    }

    public long solve1() {
        return tickets.nearbyTickets.stream()
                .flatMapToLong(ticket -> validator.invalidValues(ticket).stream().mapToLong(value -> value))
                .sum();
    }

    public long solve2() {
        List<List<Integer>> validNearbyTickets = tickets.nearbyTickets.stream()
                .filter(ticket -> validator.invalidValues(ticket).size() == 0)
                .collect(Collectors.toUnmodifiableList());

        Map<Field, Set<Integer>> fieldToPotentialIndicesMap = tickets.fields.stream()
                .collect(Collectors.toMap(Function.identity(),
                        field -> IntStream.range(0, tickets.fields.size()).boxed().collect(Collectors.toSet())));

        for (List<Integer> ticket : validNearbyTickets) {
            for (Field field : tickets.fields) {
                Set<Integer> potentialIndices = fieldToPotentialIndicesMap.get(field);

                Iterator<Integer> iterator = potentialIndices.iterator();
                while (iterator.hasNext()) {
                    int value = ticket.get(iterator.next());

                    if (!fieldValidator.isValid(field, value)) {
                        iterator.remove();
                    }
                }
            }
        }

        Map<Field, Integer> fieldToIndexMap = new HashMap<>();

        while (fieldToIndexMap.size() != tickets.fields.size()) {
            Map.Entry<Field, Set<Integer>> entryWithOneIndex = fieldToPotentialIndicesMap.entrySet().stream()
                    .filter(entry -> entry.getValue().size() == 1)
                    .findFirst().orElseThrow();

            Field fieldWithOneIndex = entryWithOneIndex.getKey();
            Integer index = entryWithOneIndex.getValue().stream().findFirst().orElseThrow();
            fieldToIndexMap.put(fieldWithOneIndex, index);
            fieldToPotentialIndicesMap.remove(fieldWithOneIndex);

            fieldToPotentialIndicesMap.forEach((field, indices) -> indices.remove(index));
        }

        return fieldToIndexMap.entrySet().stream()
                .filter(entry -> entry.getKey().name.startsWith("departure"))
                .mapToLong(entry -> tickets.ticket.get(entry.getValue()))
                .reduce(1L, (left, right) -> left * right);
    }
}
