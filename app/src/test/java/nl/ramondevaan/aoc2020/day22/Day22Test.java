package nl.ramondevaan.aoc2020.day22;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test {

    static Day22 day22;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day22Test.class.getResource("/input/day_22.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day22 = new Day22(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(33400, day22.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(33745, day22.solve2());
    }

}