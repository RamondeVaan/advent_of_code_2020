package nl.ramondevaan.aoc2020.day05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    Day05 day05;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_05.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day05 = new Day05(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(816L, day05.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(539L, day05.solve2());
    }

}