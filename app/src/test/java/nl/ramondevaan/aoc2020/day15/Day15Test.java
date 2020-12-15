package nl.ramondevaan.aoc2020.day15;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    static Day15 day15;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day15Test.class.getResource("/input/day_15.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day15 = new Day15(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(1522L, day15.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(18234L, day15.solve2());
    }

}