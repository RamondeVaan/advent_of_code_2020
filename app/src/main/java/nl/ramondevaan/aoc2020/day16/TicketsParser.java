package nl.ramondevaan.aoc2020.day16;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TicketsParser implements Parser<List<String>, Tickets> {
    private static final String FIELD_REGEX = "(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)";
    private static final Pattern FIELD_PATTERN = Pattern.compile(FIELD_REGEX);

    @Override
    public Tickets parse(List<String> lines) {
        List<List<String>> partition = new BlankStringPartitioner().partition(lines);

        List<String> fieldStrings = partition.get(0);
        String ticketString = partition.get(1).get(1);
        List<String> nearbyTicketStrings = partition.get(2);
        nearbyTicketStrings = nearbyTicketStrings.subList(1, nearbyTicketStrings.size());

        List<Field> fields = fieldStrings.stream()
                .map(TicketsParser::parseField)
                .collect(Collectors.toUnmodifiableList());

        List<Integer> ticket = parseTicket(ticketString);

        List<List<Integer>> nearbyTickets = nearbyTicketStrings.stream()
                .map(TicketsParser::parseTicket)
                .collect(Collectors.toUnmodifiableList());

        return new Tickets(fields, ticket, nearbyTickets);
    }

    private static Field parseField(String line) {
        Matcher matcher = FIELD_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalStateException();
        }

        String name = matcher.group(1);

        List<Range> ranges = List.of(new Range(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))),
                new Range(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5))));

        return new Field(name, ranges);
    }

    private static List<Integer> parseTicket(String line) {
        return Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
    }
}
