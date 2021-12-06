import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {

    public static void main(String[] args) {
        String path = args[0];
        int days = Integer.parseInt(args[1]);

        ModelGrowthRate model = new ModelGrowthRate(path);
        System.out.println(model.calculatePopulation(days));
    }

}

class ModelGrowthRate {

    private String path;
    private Map<Integer, Long> population = new HashMap<>();

    public ModelGrowthRate(String path) {
        this.path = path;
        init();
    }

    private void init() {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String[] timers = scanner.nextLine().split(",");
                for (String t : timers) {
                    Integer timerInt = Integer.parseInt(t);
                    Long value = population.containsKey(timerInt) ? population.get(timerInt) : 0;
                    population.put(timerInt, value + 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }

    public Long calculatePopulation(int days) {
        for (int i = 0; i < days; i++) {
            Map<Integer, Long> temp = new HashMap<>();
            for (Integer timer : population.keySet()) {
                if (timer == 0) {
                    temp.put(6, population.get(timer));
                    temp.put(8, population.get(timer));
                } else {
                    int newTimer = timer - 1;
                    Long pop = population.get(timer);
                    Long value = temp.containsKey(newTimer) ? temp.get(newTimer) : 0;
                    temp.put(newTimer, value + pop);
                }
            }
            population = temp;
        }
        return population.values().stream().mapToLong(n -> n).sum();
    }
}

