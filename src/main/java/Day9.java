import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day9 {

    private static final List<Point> lowPoints = new ArrayList<>();

    public static void main(String[] args) {
        int[][] map = loadMap("src/main/resources/day9.txt");
        findLowPoints(map);
        int risk = lowPoints.stream().mapToInt(lp -> lp.getValue() + 1).sum();
        System.out.println(risk);
    }

    private static void findLowPoints(int[][] map) {
        int row = map.length;
        int column = map[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (isLowPoint(i, j, map)) {
                    lowPoints.add(new Point(i, j, map[i][j]));
                }
            }
        }
    }

    private static boolean isLowPoint(int i, int j, int[][] map) {
        int row = map.length;
        int column = map[0].length;
        int up = i - 1 < 0 ? 9 : map[i - 1][j];
        int right = j + 1 > column - 1 ? 9 : map[i][j + 1];
        int down = i + 1 > row - 1 ? 9 : map[i + 1][j];
        int left = j - 1 < 0 ? 9 : map[i][j - 1];
        int value = map[i][j];
        return value < up && value < right && value < down && value < left;
    }

    private static int[][] loadMap(String path) {
        String[][] map = createMap(path);
        try (Scanner scanner = new Scanner(new File(path))) {
            int index = 0;
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split("");
                map[index++] = row;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + path);
        }
        return convertToInt(map);
    }

    private static int[][] convertToInt(String[][] map) {
        int[][] res = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                res[i][j] = Integer.parseInt(map[i][j]);
            }
        }
        return res;
    }

    private static String[][] createMap(String path) {

        int rows = 0;
        int columns = 0;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                if (columns == 0) {
                    columns = scanner.nextLine().length();
                } else {
                    scanner.nextLine();
                }
                rows++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + path);
        }
        return new String[rows][columns];
    }
}

class Point {

    private final int x;
    private final int y;
    private final int value;

    public Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
