package nl.ramondevaan.aoc2020.Day20;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    static Day20 day20;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        Path path = Path.of(Day20Test.class.getResource("/input/day_20.txt").toURI());
        List<String> lines = Files.readAllLines(path);
        day20 = new Day20(lines);
    }

    @Test
    void puzzle1() {
        assertEquals(20899048083289L, day20.solve1());
    }

    @Test
    void puzzle2() {
        //Not 1681
        assertEquals(1576L, day20.solve2());
    }

}