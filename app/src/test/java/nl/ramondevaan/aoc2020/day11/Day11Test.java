package nl.ramondevaan.aoc2020.day11;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    static Day11 day11;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day11Test.class.getResource("/input/day_11.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day11 = new Day11(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(2281L, day11.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(2085L, day11.solve2());
    }

}