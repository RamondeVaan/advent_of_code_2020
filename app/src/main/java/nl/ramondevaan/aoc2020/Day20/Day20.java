package nl.ramondevaan.aoc2020.Day20;

import nl.ramondevaan.aoc2020.util.Parser;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 {
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
        String seaMonster = seaMonster(tiles.fieldWidth);
        String seaMonsterRegex = seaMonsterRegex(seaMonster);
        List<Integer> replacementIndices = seaMonsterReplacementIndices(seaMonster);

        Pattern pattern = Pattern.compile(seaMonsterRegex);
        String image = tiles.toImage();
        Matcher matcher = pattern.matcher(image);

        while (matcher.find()) {
            int fromIndex = matcher.start();
            char[] chars = image.toCharArray();
            replacementIndices.stream()
                    .mapToInt(index -> fromIndex + index)
                    .forEach(index -> chars[index] = 'O');
            image = new String(chars);
        }

        return image;
    }

    private String seaMonster(int fieldWidth) {
        String[] seaMonster = new String[]{
                "                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "
        };
        int seaMonsterLength = Arrays.stream(seaMonster).mapToInt(String::length).max().orElseThrow();
        int difference = fieldWidth - seaMonsterLength;

        return Arrays.stream(seaMonster)
                .map(line -> StringUtils.rightPad(line, seaMonsterLength, ' '))
                .collect(Collectors.joining(" ".repeat(difference)));
    }

    private String seaMonsterRegex(String seaMonster) {
        return seaMonster.replace(' ', '.').replace("#", "(?:#|O)");
    }

    private List<Integer> seaMonsterReplacementIndices(String seaMonster) {
        return IntStream.range(0, seaMonster.length()).filter(index -> seaMonster.charAt(index) == '#').boxed()
                .collect(Collectors.toUnmodifiableList());
    }
}
