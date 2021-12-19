import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {

    private static int minX;
    private static int maxX;
    private static int minY;
    private static int maxY;

    public static void main(String[] args) {
        String path = "src/main/resources/day17example.txt";
        System.out.printf("The highest y position is %d.", partOne(path));
        System.out.println();
    }

    private static int partOne(String path) {
        String line = readLine(path);
        extractCoordinates(line);
        int x = calculateX();
        int y = calculateY();
        int highest = 0;
        for (int i = 1; i <= y; i++) {
            highest += i;
        }
        return highest;
    }

    private static String readLine(String path) {
        String line = null;
        try (Scanner sc = new Scanner(new File(path))) {
            if (sc.hasNext()) {
                line = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        return line;
    }

    private static void extractCoordinates(String line) {
        Pattern p = Pattern.compile("x=(.+)\\.\\.(.+), y=(.+)\\.\\.(.+)$");
        Matcher m = p.matcher(line);
        while (m.find()) {
            int a = Integer.parseInt(m.group(1));
            int b = Integer.parseInt(m.group(2));
            int c = Integer.parseInt(m.group(3));
            int d = Integer.parseInt(m.group(4));
            minX = Math.min(a, b);
            maxX = Math.max(a, b);
            minY = Math.min(c, d);
            maxY = Math.max(c, d);
        }
    }

    private static int calculateX() {
        int sum = 0;
        int d = Math.min(Math.abs(minX), Math.abs(maxX));
        int sign = minX / Math.abs(minX) == 1 ? 1 : -1;
        int x = 0;
        while (sum < d) {
            x += 1;
            sum += x;
        }
        return x * sign;
    }

    private static int calculateY() {
        if (minY > 0) {
            return minY;
        } else if (maxY < 0){
            return (-1 * minY) - 1;
        }
        return Integer.MAX_VALUE;
    }

    private static int partTwo() {
        
    }
}
