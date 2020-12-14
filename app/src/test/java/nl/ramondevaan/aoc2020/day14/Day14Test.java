package nl.ramondevaan.aoc2020.day14;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {

    static Day14 day14;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day14Test.class.getResource("/input/day_14.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day14 = new Day14(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(12512013221615L, day14.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(3905642473893L, day14.solve2());
    }

}