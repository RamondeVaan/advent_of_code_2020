package nl.ramondevaan.aoc2020.day01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

    Day01 day01;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day01 = Day01.builder()
                .sum(2020)
                .values(Files.lines(Path.of(getClass().getResource("/input/day_01.txt").toURI()))
                        .map(Long::parseLong)
                        .collect(Collectors.toList()))
                .build();
    }

    @Test
    void puzzle1() {
        assertEquals(157059L, day01.solve(2));
    }

    @Test
    void puzzle2() {
        assertEquals(165080960L, day01.solve(3));
    }
}