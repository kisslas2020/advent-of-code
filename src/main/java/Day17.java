import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {

    private static int minX;
    private static int maxX;
    private static int minY;
    private static int maxY;

    public static void main(String[] args) {
        String path = "src/main/resources/day17.txt";
        System.out.printf("The highest y position is %d.", partOne(path));
        System.out.println();
        System.out.println(partTwo());
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
            return maxY;
        } else if (maxY < 0){
            return (-1 * minY) - 1;
        }
        return Integer.MAX_VALUE;
    }

    private static int partTwo() {
        List<int[]> coordinates = new ArrayList<>();
        int xMinBase = maxX > 0 ? 0 : minX;
        int xMaxBase = maxX > 0 ? maxX : 0;
        int yMinBase = minY;
        int yMaxBase = minY > 0 ? maxY : (-1 * minY) - 1;
        for (int xBase = xMinBase; xBase <= xMaxBase; xBase++) {
            for (int yBase = yMinBase; yBase <= yMaxBase; yBase++) {
                int index = 0;
                while (true) {
                    int x = getXPosition(xBase, index);
                    int y = getYPosition(yBase, index);
                    if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                        coordinates.add(new int[]{xBase, yBase});
                        break;
                    }
                    if (x < xMinBase || y < yMinBase) {
                        break;
                    }
                    index++;
                }
            }

        }
        return coordinates.size();
    }

    private static int getXPosition(int base, int index) {
        int res = 0;
        for (int i = 0; i < index; i++) {
            res += base - i < 0 ? 0 : base - i;
        }
        return res;
    }

    private static int getYPosition(int base, int index) {
        int res = 0;
        for (int i = 0; i < index; i++) {
            res += base - i;
        }
        return res;
    }
}
