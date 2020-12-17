package nl.ramondevaan.aoc2020.day17;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CubesParser implements Parser<List<String>, Set<CoordinateN>> {

    private static final char ACTIVE_CHAR = '#';

    @Override
    public Set<CoordinateN> parse(List<String> lines) {
        Set<CoordinateN> ret = new HashSet<>();

        int x;
        int y = 0;

        for (String line : lines) {
            char[] chars = line.toCharArray();
            for (x = 0; x < chars.length; x++) {
                if (chars[x] == ACTIVE_CHAR) {
                    ret.add(CoordinateN.of(x, y));
                }
            }
            y++;
        }

        return Collections.unmodifiableSet(ret);
    }
}
