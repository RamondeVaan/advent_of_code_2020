package nl.ramondevaan.aoc2020.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {
    Day13 day13;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_13.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day13 = new Day13(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(115L, day13.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(136797L, day13.solve2());
    }

}