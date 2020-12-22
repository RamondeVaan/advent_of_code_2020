package nl.ramondevaan.aoc2020.day22;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CardsParser implements Parser<List<String>, List<Integer>> {
    List<Integer> a;
    List<Integer> b;

    @Override
    public List<Integer> parse(List<String> lines) {
        return lines.subList(1, lines.size()).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardsParser that = (CardsParser) o;
        return a.equals(that.a) && b.equals(that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
