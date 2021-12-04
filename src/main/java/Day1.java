import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1 {

    public List<Integer> readData(String path) {

        List<Integer> measurements = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))){

            while(scanner.hasNext()) {
                measurements.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return measurements;
    }

    public List<Integer> makeSumsOfThree(List<Integer> measurements) {
        List<Integer> threeMeasurements = new ArrayList<>();

        for (int i = 0; i < measurements.size() - 2; i++) {
            int sum = measurements.get(i) + measurements.get(i + 1) + measurements.get(i + 2);
            threeMeasurements.add(sum);
        }

        return threeMeasurements;
    }

    public int countIncrease(List<Integer> nums) {
        int count = 0;
        int prev = Integer.MAX_VALUE;
        for (Integer num : nums) {
            if (num > prev) {
                count++;
            }
            prev = num;
        }
        return count;
    }
}
