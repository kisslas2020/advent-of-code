import java.util.*;
import java.util.stream.Collectors;

public class Day12 {



    private void recordRoute(Queue<Cave> route) {
        String routeString = route.stream().map(r -> r.getName()).collect(Collectors.joining(","));
    }
}

class Cave {

    private final String name;
    private final boolean isBig;
    private final Map<String, Boolean> neighbors = new HashMap<>();

    public Cave(String name) {
        this.name = name;
        this.isBig = name.equals(name.toUpperCase());
    }

    public String getName() {
        return name;
    }

    public boolean isBig() {
        return isBig;
    }

    public Map<String, Boolean> getNeighbors() {
        return neighbors;
    }
}
