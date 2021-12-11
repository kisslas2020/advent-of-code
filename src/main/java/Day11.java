import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day11 {

    private static Octopus[][] octopuses;

    public static void main(String[] args) {
        readFile("src/main/resources/day11example.txt");
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
