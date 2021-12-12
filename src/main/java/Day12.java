import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {
    
    private static final List<String> routes = new ArrayList<>();
    private static final List<Cave> caves = new ArrayList<>();

    public static void main(String[] args) {
        String path = "src/main/resources/day12example1.txt";
        PartOne(path);
    }

    private static void PartOne(String path) {
        Queue<Cave> route = new LinkedList<>();
        loadCaves(path);
        findNextStep(route, caves.stream().filter(c -> c.getName().equals("start")).findFirst().get());
    }

    private static void loadCaves(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addNewCave(new Cave(line.split("-")[0]));
                addNewCave(new Cave(line.split("-")[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void addNewCave(Cave cave) {
        if (!caves.contains(cave)) {
            caves.add(cave);
        }
    }


    private static void findNextStep(Queue<Cave> route, Cave prev) {
        for (String neighbor : prev.getNeighbours()) {
            Cave cave = caves.stream().filter(c -> c.getName().equals(neighbor)).findFirst().get();
            if ((cave.isHasBeenVisited() && !cave.isBig()) || prev.getNeighbours().contains(cave)) {
                continue;
            }
            prev.visit(cave);
            route.offer(cave);
            if (cave.getName().equals("end")) {
                return;
            }
            findNextStep(route, cave);
        }
        return;
    }

    private static void recordRoute(Queue<Cave> route) {
        String routeString = route.stream().map(r -> r.getName()).collect(Collectors.joining(","));
        routes.add(routeString);
    }
}

class Cave {

    private final String name;
    private final boolean isBig;
    private boolean hasBeenVisited;
    private final List<String> neighbours = new ArrayList<>();
    private final List<String> visitedCave = new ArrayList<>();

    public Cave(String name) {
        this.name = name;
        this.isBig = name.equals(name.toUpperCase());
    }

    public void visit(Cave next) {
        visitedCave.add(next.getName());
    }

    public String getName() {
        return name;
    }

    public boolean isBig() {
        return isBig;
    }

    public boolean isHasBeenVisited() {
        return hasBeenVisited;
    }

    public void setHasBeenVisited(boolean hasBeenVisited) {
        this.hasBeenVisited = hasBeenVisited;
    }

    public List<String> getVisitedCave() {
        return visitedCave;
    }

    public List<String> getNeighbours() {
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return name.equals(cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
