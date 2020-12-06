package nl.ramondevaan.aoc2020.day01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    Day01 day01;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_01.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day01 = new Day01(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(157059L, day01.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(165080960L, day01.solve2());
    }
}