import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    private static Map<String, String> collection = new HashMap<>();
    private static Map<String, Integer> score = new HashMap<>();
    private static List<String> completions = new ArrayList<>();

    public static void main(String[] args) {
        init();
        int sum = partOne("src/main/resources/day10.txt");
        System.out.println("Total syntax error score: " + sum);
        long middleScore = calculateMiddleScore();
        System.out.println("Middle score: " + middleScore);
    }

    private static long calculateMiddleScore() {
        List<Long> scores = new ArrayList<>();
        Map<String, Integer> values = new HashMap<>();
        values.put(")", 1);
        values.put("]", 2);
        values.put("}", 3);
        values.put(">", 4);
        for (String line : completions) {
            String[] brackets = line.split("");
            long sum = 0L;
            for (String bracket : brackets) {
                sum = sum * 5 + values.get(bracket);
            }
            scores.add(sum);
        }
        scores.sort(Comparator.naturalOrder());
        int length = scores.size();
        return scores.get(length / 2);
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
            if (collection.containsKey(s)) {
                stack.add(s);
            } else if (stack.empty() || !collection.get(stack.pop()).equals(s)){
                return s;
            }
        }
        buildCompletion(line, stack);
        return "";
    }

    private static void buildCompletion(String line, Stack<String> stack) {
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(collection.get(stack.pop()));
        }
        completions.add(String.valueOf(sb));
    }
}
