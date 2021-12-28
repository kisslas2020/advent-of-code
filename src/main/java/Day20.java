import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day20 {

    private static String[] imageEnhancementAlgorithm;

    public static void main(String[] args) {
        String path = "src/main/resources/day20example.txt";
        imageEnhancementAlgorithm = loadAlgorithm(path);
    }

    private static String[] loadAlgorithm(String path) {
        try (Scanner sc = new Scanner(new File(path))){
            if (sc.hasNext()) {
                return sc.nextLine().split("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }


}
