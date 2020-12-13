package nl.ramondevaan.aoc2020.day07;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    static Day07 day07;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day07Test.class.getResource("/input/day_07.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day07 = new Day07(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(370L, day07.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(29547L, day07.solve2());
    }

}