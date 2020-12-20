package nl.ramondevaan.aoc2020.Day20;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 {

    private static final String ESCAPED_LINE_SEPARATOR = StringEscapeUtils.escapeJava(System.lineSeparator());

    private final Tiles initialTiles;

    public Day20(List<String> lines) {
        Parser<List<String>, Tiles> parser = new TilesParser();
        initialTiles = parser.parse(lines);
    }

    public long solve1() {
        return initialTiles.getCornerTileIds().stream()
                .reduce(1L, (left, right) -> left * right);
    }

    public long solve2() {
        Tiles tiles = initialTiles;

        String image;

        for (int i = 0; i < 4; i++) {
            image = findAndReplaceSeaMonsters(tiles);
            if (seaMonstersFound(image)) {
                return countHash(image);
            }
            tiles = tiles.rotateRight();
        }

        tiles = tiles.flipLeftRight();

        for (int i = 0; i < 4; i++) {
            image = findAndReplaceSeaMonsters(tiles);
            if (seaMonstersFound(image)) {
                return countHash(image);
            }
            tiles = tiles.rotateRight();
        }

        throw new IllegalStateException();
    }

    private long countHash(String image) {
        return image.chars().filter(character -> character == '#').count();
    }

    private boolean seaMonstersFound(String s) {
        return s.contains("O");
    }

    private String findAndReplaceSeaMonsters(Tiles tiles) {
        String[] seaMonster = seaMonster();
        String seaMonsterRegex = seaMonsterRegex(seaMonster, tiles.fieldWidth);
        List<Integer> replacementIndices = seaMonsterReplacementIndices(seaMonster, tiles.fieldWidth);

        Pattern pattern = Pattern.compile(seaMonsterRegex);
        String image = tiles.toImage();
        Matcher matcher = pattern.matcher(image);

        int from = 0;
        while (matcher.find(from)) {
            int fromIndex = matcher.start();
            from = matcher.start() + 1;
            char[] chars = image.toCharArray();
            replacementIndices.stream()
                    .mapToInt(index -> fromIndex + index)
                    .forEach(index -> chars[index] = 'O');
            image = new String(chars);
        }

        return image;
    }

    private String[] seaMonster() {
        String[] seaMonster = new String[]{
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
        };
        int seaMonsterLength = Arrays.stream(seaMonster).mapToInt(String::length).max().orElseThrow();

        return Arrays.stream(seaMonster)
                .map(line -> StringUtils.rightPad(line, seaMonsterLength, ' '))
                .toArray(String[]::new);
    }

    private String seaMonsterRegex(String[] seaMonster, int fieldSize) {
        int difference = fieldSize - seaMonster[0].length() + 1;
        return Arrays.stream(seaMonster).map(line -> line.replace(' ', '.'))
                .collect(Collectors.joining(String.format("(.|%s){%d}", ESCAPED_LINE_SEPARATOR, difference)));
    }

    private List<Integer> seaMonsterReplacementIndices(String[] seaMonster, int fieldSize) {
        return IntStream.range(0, seaMonster.length).flatMap(lineIndex -> {
            int offset = lineIndex * (fieldSize + System.lineSeparator().length());
            String line = seaMonster[lineIndex];
            return IntStream.range(0, line.length())
                    .filter(index -> line.charAt(index) == '#')
                    .map(index -> index + offset);
        }).boxed().collect(Collectors.toUnmodifiableList());
    }
}
