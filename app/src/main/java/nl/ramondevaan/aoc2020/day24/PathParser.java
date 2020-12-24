package nl.ramondevaan.aoc2020.day24;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PathParser implements Parser<String, List<Direction>> {
    @Override
    public List<Direction> parse(String toParse) {
        AtomicInteger index = new AtomicInteger(0);

        List<Direction> directions = new ArrayList<>();

        while (index.get() < toParse.length()) {
            directions.add(parse(toParse, index));
        }

        return Collections.unmodifiableList(directions);
    }

    private Direction parse(String toParse, AtomicInteger index) {
        char current = toParse.charAt(index.getAndIncrement());
        char extra;

        switch (current) {
            case 'e':
                return Direction.EAST;
            case 'w':
                return Direction.WEST;
            case 'n':
                extra = toParse.charAt(index.getAndIncrement());
                if (extra == 'e') {
                    return Direction.NORTH_EAST;
                }
                return Direction.NORTH_WEST;
            case 's':
                extra = toParse.charAt(index.getAndIncrement());
                if (extra == 'e') {
                    return Direction.SOUTH_EAST;
                }
                return Direction.SOUTH_WEST;
        }

        throw new IllegalStateException();
    }
}
