import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    private static Map<String, String> collection = new HashMap<>();
    private static Map<String, Integer> score = new HashMap<>();

    {
        collection.put("(", ")");
        collection.put("[", "]");
        collection.put("{", "}");
        collection.put("<", ">");
        score.put(")", 3);
        score.put("]", 57);
        score.put("}", 1197);
        score.put(">", 25137);
    }

    public static void main(String[] args) {
        partOne("src/main/resources/day10example.txt");
    }

    private static void partOne(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String res = checkLine(scanner.nextLine());

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }

    private static String checkLine(String line) {

        String[] brackets = line.split("");
        Stack<String> stack = new Stack<>();
        for (String s : brackets) {
            if (collection.containsKey(s)) {
                stack.add(s);
            } else if (!collection.get(stack.pop()).equals(s)){
                return s;
            }
        }
        return "";
    }
}
