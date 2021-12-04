import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {

    private int x = 0;
    private int y = 0;
    private int aim = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void readData(String path){
        try(Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String direction = scanner.next();
                int move = scanner.nextInt();
                calculatePosition(direction, move);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void calculatePosition(String direction, int move){
        switch (direction) {
            case "forward":
                x += move;
                y += aim * move;
                break;
            case "down":
                aim += move;
                break;
            case "up":
                aim -= move;
                break;
        }
    }
}
