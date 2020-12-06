package nl.ramondevaan.aoc2020.day03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    Day03 day03;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_03.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day03 = new Day03(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(145L, day03.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(3424528800L, day03.solve2());
    }
}