package nl.ramondevaan.aoc2020.day05;

import nl.ramondevaan.aoc2020.util.Parser;

public class SeatParser implements Parser<String, Seat> {

    @Override
    public Seat parse(String toParse) {
        String numberString = toParse.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1');

        int seatId = Integer.parseInt(numberString, 2);
        int row = seatId / 8;
        int column = seatId % 8;

        return new Seat(seatId, row, column);
    }
}
