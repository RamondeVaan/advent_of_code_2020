package nl.ramondevaan.aoc2020.day19;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {

    static Day19 day19;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day19Test.class.getResource("/input/day_19.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day19 = new Day19(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(195L, day19.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(309L, day19.solve2());
    }

}