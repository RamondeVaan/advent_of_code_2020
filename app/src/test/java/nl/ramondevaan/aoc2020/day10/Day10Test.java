package nl.ramondevaan.aoc2020.day10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    Day10 day10;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_10.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day10 = new Day10(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(21806024L, day10.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(2986195L, day10.solve2());
    }
}
