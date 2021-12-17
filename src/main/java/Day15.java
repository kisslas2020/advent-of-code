import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day15 {

    private static Node[][] riskMap;

    public static void main(String[] args) {
        String path = "src/main/resources/day15.txt";
        makeRiskMap(path);
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    private static int partOne() {
        Queue<Node> queue = new PriorityQueue<>();
        queue.add(initStart());
        Node end = initEnd();
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            current.setVisited(true);
            if (current.equals(end)) {
                return current.getTotalValue();
            }
            current.setNeighbors(findNeighbors(current));
            for (Node next : current.getNeighbors()) {
                if (next.isVisited()) {
                    continue;
                }
                int total = current.getTotalValue() + next.getValue();
                if (total < next.getTotalValue()) {
                    next.setTotalValue(total);
                    next.setPrevious(current);
                }
                queue.remove(next);
                queue.add(next);
            }
        }
        return 0;
    }

    private static int partTwo() {
        makeLargeMap();
        return partOne();
    }

    private static void makeRiskMap(String path) {
        measure(path);
        int height = 0;
        try (Scanner sc = new Scanner(new File(path))){
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("");
                for (int width = 0; width < line.length; width++) {
                    riskMap[height][width] = new Node(height, width, Integer.parseInt(line[width]));
                }
                height++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        System.out.println("ready");
    }

    private static void makeLargeMap() {
        int x = riskMap[0].length;
        int y = riskMap.length;
        Node[][] newRiskMap = new Node[y * 5][x * 5];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                int value = riskMap[i][j].getValue();
                for (int m = 0; m < 5; m++) {
                    for (int n = 0; n < 5; n++) {
                        int t = value + m + n;
                        int newValue = t > 9 ? t - 9 : t;
                        int newY = i + m * y;
                        int newX = j + n * x;
                        newRiskMap[newY][newX] = new Node(newY, newX, newValue);
                    }
                }
            }
        }
        riskMap = newRiskMap;
    }

    private static void measure(String path) {
        int width = 0;
        int height = 0;
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNext()) {
                if (width == 0) {
                    width = sc.nextLine().length();
                } else {
                    sc.nextLine();
                }
                height++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        riskMap = new Node[height][width];
    }

    private static Node initStart() {
        Node start = riskMap[0][0];
        start.setTotalValue(0);
        start.setPrevious(null);
        start.setVisited(true);
        return start;
    }

    private static Node initEnd() {
        int x = riskMap[0].length - 1;
        int y = riskMap.length - 1;

        return riskMap[y][x];
    }

    private static Set<Node> findNeighbors(Node node) {
        int x = node.getX();
        int y = node.getY();
        int maxX = riskMap[0].length - 1;
        int maxY = riskMap.length - 1;
        Set<Node> neighbors = new HashSet<>();

        if (x < maxX) {
            neighbors.add(riskMap[y][x + 1]);
        }
        if (x > 0) {
            neighbors.add(riskMap[y][x - 1]);
        }
        if (y < maxY) {
            neighbors.add(riskMap[y + 1][x]);
        }
        if (y > 0) {
            neighbors.add(riskMap[y - 1][x]);
        }
        return neighbors;
    }
}

class Node implements Comparable<Node> {

    private final int x;
    private final int y;
    private final int value;
    private int totalValue;
    private Set<Node> neighbors = new HashSet<>();
    private Node previous;
    private boolean visited;

    public Node(int y, int x, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.totalValue = Integer.MAX_VALUE;
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

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public int compareTo(Node o) {
        return this.totalValue - o.getTotalValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
