package nl.ramondevaan.aoc2020.util;

public class ArrayUtil {

    public static int[][] rotateRight(int[][] data) {
        int[][] newData = new int[data.length][data.length];
        int lengthMinus1 = data.length - 1;

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data.length; x++) {
                newData[x][lengthMinus1 - y] = data[y][x];
            }
        }

        return newData;
    }

    public static int[][] rotateLeft(int[][] data) {
        int[][] newData = new int[data.length][data.length];
        int lengthMinus1 = data.length - 1;

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data.length; x++) {
                newData[lengthMinus1 - x][y] = data[y][x];
            }
        }

        return newData;
    }

    public static int[][] rotateTwice(int[][] data) {
        int[][] newData = new int[data.length][data.length];
        int lengthMinus1 = data.length - 1;

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data.length; x++) {
                newData[lengthMinus1 - y][lengthMinus1 - x] = data[y][x];
            }
        }

        return newData;
    }

    public static int[][] flipLeftRight(int[][] data) {
        int lengthMinus1 = data.length - 1;
        int[][] newData = new int[data.length][data.length];

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data.length; x++) {
                newData[y][lengthMinus1 - x] = data[y][x];
            }
        }

        return newData;
    }

    public static int[][] flipTopBottom(int[][] data) {
        int lengthMinus1 = data.length - 1;
        int[][] newData = new int[data.length][data.length];

        for (int y = 0; y < data.length; y++) {
            System.arraycopy(data[y], 0, newData[lengthMinus1 - y], 0, data.length);
        }

        return newData;
    }
}
