package nl.ramondevaan.aoc2020.day04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    Day04 day04;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day04 = Day04.builder()
                .lines(Files.lines(Path.of(getClass().getResource("/input/day_04.txt").toURI()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Test
    void puzzle1() {
        assertEquals(226L, day04.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(160L, day04.solve2());
    }
}