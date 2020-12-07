package nl.ramondevaan.aoc2020.day07;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "color")
public class Bag {
    private String color;
    private Set<Bag> parents;
    private Map<Bag, Integer> bags;
}
