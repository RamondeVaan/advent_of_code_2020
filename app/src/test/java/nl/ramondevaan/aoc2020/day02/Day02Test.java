package nl.ramondevaan.aoc2020.day02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    Day02 day02;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        day02 = Day02.builder()
                .passwords(Files.lines(Path.of(getClass().getResource("/input/day_02.txt").toURI()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Test
    void puzzle1() {
        assertEquals(528L, day02.solve1());
    }

    @Test
    void puzzle2() {
        assertEquals(497L, day02.solve2());
    }

}