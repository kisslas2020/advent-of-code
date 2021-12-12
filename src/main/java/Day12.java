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
        String path = "src/main/resources/day12.txt";
        PartOne(path);
    }

    private static void PartOne(String path) {
        Stack<Cave> route = new Stack<>();
        loadCaves(path);
        Cave start = caves.stream().filter(c -> c.getName().equals("start")).findFirst().get();
        start.setHasBeenVisited(true);
        route.add(start);
        findNextStep(route, start);
        System.out.printf("The number of paths is %d.", routes.size());

    }

    private static void loadCaves(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Cave c1 = new Cave(line.split("-")[0]);
                Cave c2 = new Cave(line.split("-")[1]);
                addNewCave(c1, c2);
                addNewCave(c2, c1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void addNewCave(Cave c1, Cave c2) {
        if (!caves.contains(c1)) {
            caves.add(c1);
        } else {
            Cave finalC = c1;
            c1 = caves.stream().filter(c -> c.getName().equals(finalC.getName())).findFirst().get();
        }
        c1.addNeighbor(c2.getName());
    }

    private static void findNextStep(Stack<Cave> route, Cave prev) {
        for (String neighbor : prev.getNeighbours()) {
            Cave cave = caves.stream().filter(c -> c.getName().equals(neighbor)).findFirst().get();
            if ((cave.isHasBeenVisited() && !cave.isBig()) || prev.getVisitedCave().contains(cave)) {
                continue;
            }
            prev.visit(cave);
            cave.setHasBeenVisited(true);
            route.add(cave);
            if (cave.getName().equals("end")) {
                recordRoute(route);
                cave.setHasBeenVisited(false);
                route.pop();
                continue;
            }
            findNextStep(route, cave);
        }
        route.pop().setHasBeenVisited(false);
        return;
    }

    private static void recordRoute(Stack<Cave> route) {
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

    public void addNeighbor(String neighbor) {
        neighbours.add(neighbor);
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
