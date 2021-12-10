import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {

    private static final List<Point> lowPoints = new ArrayList<>();

    public static void main(String[] args) {
        int[][] map = loadMap("src/main/resources/day9.txt");
        findLowPoints(map);
        int risk = lowPoints.stream().mapToInt(lp -> lp.getValue() + 1).sum();
        System.out.println("Total risk is:" + risk);
        List<Basin> basins = lowPoints.stream().map(lp -> getNeighbors(lp, map, new Basin(lp))).collect(Collectors.toList());
        basins.sort(new Comparator<Basin>() {
            @Override
            public int compare(Basin o1, Basin o2) {
                return o2.getBasinPoints().size() - o1.getBasinPoints().size();
            }
        });
        int b1 = basins.get(0).getBasinPoints().size() + 1;
        int b2 = basins.get(1).getBasinPoints().size() + 1;
        int b3 = basins.get(2).getBasinPoints().size() + 1;
        System.out.println("The product of the three largest basin's size: " + b1 * b2 * b3);
    }

    private static Basin getNeighbors(Point point, int[][] map, Basin basin) {
        int row = point.getX();
        int col = point.getY();
        int value = map[row][col];

        if (row > 0) {
            int newValue = map[row - 1][col];
            if (newValue < 9 && newValue > value) {
                Point newPoint = new Point(row - 1, col, newValue);
                getNeighbors(newPoint, map, basin);
                basin.addBasinPoint(newPoint);
                point.addNeighbor(newPoint);
            }
        }
        if (col < map[0].length - 1) {
            int newValue = map[row][col + 1];
            if (newValue < 9 && newValue > value) {
                Point newPoint = new Point(row, col + 1, newValue);
                getNeighbors(newPoint, map, basin);
                basin.addBasinPoint(newPoint);
                point.addNeighbor(newPoint);
            }
        }
        if (row < map.length - 1) {
            int newValue = map[row + 1][col];
            if (newValue < 9 && newValue > value) {
                Point newPoint = new Point(row + 1, col, newValue);
                getNeighbors(newPoint, map, basin);
                basin.addBasinPoint(newPoint);
                point.addNeighbor(newPoint);
            }
        }
        if (col > 0) {
            int newValue = map[row][col - 1];
            if (newValue < 9 && newValue > value) {
                Point newPoint = new Point(row, col - 1, newValue);
                getNeighbors(newPoint, map, basin);
                basin.addBasinPoint(newPoint);
                point.addNeighbor(newPoint);
            }
        }
        return basin;
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
    private List<Point> neighbors = new ArrayList<>();

    public Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public void addNeighbor(Point point) {
        neighbors.add(point);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Basin {

    private final Point lowPoint;
    private final Set<Point> basinPoints = new HashSet<>();

    public Basin(Point lowPoint) {
        this.lowPoint = lowPoint;
    }

    public void addBasinPoint(Point point) {
        basinPoints.add(point);
    }

    public Point getLowPoint() {
        return lowPoint;
    }

    public Set<Point> getBasinPoints() {
        return basinPoints;
    }
}
