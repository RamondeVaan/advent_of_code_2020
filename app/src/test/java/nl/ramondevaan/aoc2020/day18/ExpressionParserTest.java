package nl.ramondevaan.aoc2020.day18;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ExpressionParserTest {

    static ExpressionParser parser;

    @BeforeAll
    static void setUp() {
        parser = new ExpressionParser(Map.of(
                ExpressionType.NUMBER, 4,
                ExpressionType.ADDITION, 1,
                ExpressionType.MULTIPLICATION, 2,
                ExpressionType.PARENTHESES, 3
        ));
    }

    @Test
    void parse() {
        Assertions.assertEquals(6L, parser.parse("1 + 2 + 3").compute());
        Assertions.assertEquals(5L, parser.parse("1 * 2 + 3").compute());
        Assertions.assertEquals(7L, parser.parse("1 + 2 * 3").compute());
        Assertions.assertEquals(17L, parser.parse("5 * 2 + 3 + 4").compute());
        Assertions.assertEquals(15L, parser.parse("5 + 2 * 3 + 4").compute());
        Assertions.assertEquals(19L, parser.parse("5 + 2 + 3 * 4").compute());
    }
}