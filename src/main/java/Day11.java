import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Day11 {

    private static Octopus[][] octopuses;

    public static void main(String[] args) {
        readFile("src/main/resources/day11example.txt");
        for (int i = 0; i < 10; i++) {
            singleStep();
            printStanding();
        }
    }

    private static void printStanding() {
        for (int i = 0; i < octopuses.length; i++) {
            for (int j = 0; j < octopuses[0].length; j++) {
                System.out.print(" " + octopuses[i][j].getValue());
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int singleStep() {
        Arrays.stream(octopuses).forEach(row -> Arrays.stream(row).forEach(o -> {
            o.setCanFlash(true);
            o.incrementValue();
        }));
        int numberOfFlashes = 0;
        while (true) {
            int newFlashes = checkFlashes();
            if (newFlashes == 0) {
                return numberOfFlashes;
            }
            numberOfFlashes += newFlashes;
        }
    }

    private static int checkFlashes() {
        AtomicInteger numberOfFlashes = new AtomicInteger();
        Arrays.stream(octopuses).forEach(row -> Arrays.stream(row).forEach(o -> {
            if (o.getValue() > 9) {
                numberOfFlashes.getAndIncrement();
                o.setValue(0);
                o.setCanFlash(false);
                increaseNeighbors(o);
            }
        }));
        return numberOfFlashes.get();
    }

    private static void increaseNeighbors(Octopus o) {
        int y = o.getY();
        int x = o.getX();
        int rows = octopuses.length;
        int cols = octopuses[0].length;
        int upRow = y > 0 ? y - 1 : y;
        int downRow = y < rows - 1 ? y + 1 : y;
        int leftCol = x > 0 ? x - 1 : x;
        int rightCol = x < cols - 1 ? x + 1 : x;

        for (int i = upRow; i <= downRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                if (!(i == y && j == x)) {
                    octopuses[i][j].incrementValue();
                }
            }
        }
    }

    private static void readFile(String path) {
        int rows = 0;
        int cols = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (rows == 0) {
                    rows = line.length();
                }
                cols++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadData(rows, cols, path);
    }

    private static void loadData(int rows, int cols, String path) {
        octopuses = new Octopus[rows][cols];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    octopuses[y][x] = new Octopus(y, x, reader.read() - 48);
                }
                reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class Octopus {

    private final int y;
    private final int x;
    private int value;
    private boolean canFlash;

    public Octopus(int y, int x, int value) {
        this.y = y;
        this.x = x;
        this.value = value;
        this.canFlash = true;
    }

    public void incrementValue() {
        if (this.canFlash) {
            this.value++;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCanFlash() {
        return canFlash;
    }

    public void setCanFlash(boolean canFlash) {
        this.canFlash = canFlash;
    }
}
