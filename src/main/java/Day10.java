import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    private static Map<String, String> collection = new HashMap<>();
    private static Map<String, Integer> score = new HashMap<>();





    public static void main(String[] args) {
        int sum = partOne("src/main/resources/day10example.txt");
        System.out.println("Total syntax error score: " + sum);
    }

    private static void init() {
        collection.put("(", ")");
        collection.put("[", "]");
        collection.put("{", "}");
        collection.put("<", ">");
        score.put(")", 3);
        score.put("]", 57);
        score.put("}", 1197);
        score.put(">", 25137);
        score.put("", 0);
    }

    private static int partOne(String path) {
        int sum = 0;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String res = checkLine(scanner.nextLine());
                sum += score.get(res);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        return sum;
    }

    private static String checkLine(String line) {

        String[] brackets = line.split("");
        Stack<String> stack = new Stack<>();
        for (String s : brackets) {
            System.out.println(s);
            if (collection.containsKey(s)) {
                stack.add(s);
            } else if (stack.empty() || !collection.get(stack.pop()).equals(s)){
                return s;
            }
        }
        return "";
    }
}
