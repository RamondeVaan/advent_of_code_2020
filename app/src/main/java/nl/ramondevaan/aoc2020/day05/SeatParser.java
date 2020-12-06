package nl.ramondevaan.aoc2020.day05;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.StringUtils;

public class SeatParser implements Parser<String, Seat> {

    @Override
    public Seat parse(String toParse) {
        String numberString = StringUtils.replaceEach(toParse,
                new String[]{"F", "B", "L", "R"},
                new String[]{"0", "1", "0", "1"});

        int seatId = Integer.parseInt(numberString, 2);
        int row = seatId / 8;
        int column = seatId % 8;

        return new Seat(seatId, row, column);
    }
}
