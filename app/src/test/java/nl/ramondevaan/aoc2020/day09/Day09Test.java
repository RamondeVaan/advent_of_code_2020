package nl.ramondevaan.aoc2020.day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    Day09 day09;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(getClass().getResource("/input/day_08.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day09 = new Day09(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(1930L, day09.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(1688L, day09.solve2());
    }


}