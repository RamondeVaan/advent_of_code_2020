package nl.ramondevaan.aoc2020.day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    Day08 day08;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_08.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day08 = new Day08(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(1930L, day08.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(1688L, day08.solve2());
    }

}