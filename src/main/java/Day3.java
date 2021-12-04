import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day3 {

    public List<String> readData(String file) {
        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(file))) {
            while (sc.hasNext()) {
                lines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return lines;
    }

    public int[] aggregateLines(List<String> lines) {
        int numberOfBits = lines.get(0).length();
        int[] aggregator = new int[numberOfBits];

        for (String s : lines) {
            for (int i = 0; i < numberOfBits; i++) {
                aggregator[i] += Character.getNumericValue(s.charAt(i));
            }
        }
        return aggregator;
    }

    public String calculateGamma(List<String> lines, int[] aggregator) {
        int records = lines.size();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aggregator.length; i++) {
            if (aggregator[i] > records / 2) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }

        return sb.toString();
    }

    public String calculateEpsilon(String gamma) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gamma.length(); i++) {
            if (gamma.charAt(i) == '1') {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }
        return sb.toString();
    }

    public String calculateOxygen(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        int len = lines.get(0).length();

        for (int i = 0; i < len; i++) {
            if (lines.size() == 1) {
                return lines.get(0);
            }
            int[] aggregator = aggregateLines(lines);
            char cond = aggregator[i] >= lines.size() - aggregator[i] ? '1' : '0';
            sb.append(cond);
            int finalI = i;
            lines = lines.stream().filter(n -> n.charAt(finalI) == cond).collect(Collectors.toList());
        }
        return sb.toString();
    }

    public String calculateCO2(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        int len = lines.get(0).length();

        for (int i = 0; i < len; i++) {
            if (lines.size() == 1) {
                return lines.get(0);
            }
            int[] aggregator = aggregateLines(lines);
            char cond = aggregator[i] >= lines.size() - aggregator[i] ? '0' : '1';
            sb.append(cond);
            int finalI = i;
            lines = lines.stream().filter(n -> n.charAt(finalI) == cond).collect(Collectors.toList());
        }
        return sb.toString();
    }

    public int convertBinaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }
}

