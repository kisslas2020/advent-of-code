import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {

    private static Queue<Integer> largerHalf = new PriorityQueue();
    private static Queue<Integer> smallerHalf = new PriorityQueue(Comparator.reverseOrder());

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new File("src/main/resources/day7.txt"))) {
            scanner.useDelimiter("[^\\d]");
            while (scanner.hasNext()) {
                int number = scanner.nextInt();
                add(number);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: ");
        }
        double median = calculateMedian();
        int medianInt = (int)median;
        System.out.println(calculateFuel(medianInt));
        if (median != (int) median) {
            System.out.println(calculateFuel(medianInt + 1));
        }
        int fuelCost2 = calculateFuel();
        System.out.println(fuelCost2);
    }

    private static void add(int number) {
        if (!largerHalf.isEmpty() && largerHalf.peek() > number) {
            smallerHalf.offer(number);
            if (smallerHalf.size() > largerHalf.size() + 1) {
                largerHalf.offer(smallerHalf.poll());
            }
        } else {
            largerHalf.offer(number);
            if (largerHalf.size() > smallerHalf.size() + 1) {
                smallerHalf.offer(largerHalf.poll());
            }
        }
    }

    private static double calculateMedian() {
        if (largerHalf.size() > smallerHalf.size()) {
            return largerHalf.peek();
        } else if (smallerHalf.size() > largerHalf.size()) {
            return smallerHalf.peek();
        } else {
            return (largerHalf.peek() + smallerHalf.peek()) / 2;
        }
    }

    private static int calculateFuel(int median) {
        int sum = 0;
        sum += largerHalf.stream().mapToInt(n -> Math.abs(median - n)).sum();
        sum += smallerHalf.stream().mapToInt(n -> Math.abs(median - n)).sum();
        return sum;
    }

    private static int calculateFuel() {
        int sum = largerHalf.stream().mapToInt(n -> n).sum();
        sum += smallerHalf.stream().mapToInt(n -> n).sum();
        double avg =  1.0 * sum / (largerHalf.size() + smallerHalf.size());
        int avgIntLow = (int) Math.floor(avg);
        int avgIntHigh = (int) Math.ceil(avg);
        int fuelLow = largerHalf.stream().mapToInt(n -> sumOfConsecutive(Math.abs(avgIntLow - n))).sum();
        fuelLow += smallerHalf.stream().mapToInt(n -> sumOfConsecutive(Math.abs(avgIntLow - n))).sum();
        int fuelHigh = largerHalf.stream().mapToInt(n -> sumOfConsecutive(Math.abs(avgIntHigh - n))).sum();
        fuelHigh += smallerHalf.stream().mapToInt(n -> sumOfConsecutive(Math.abs(avgIntHigh - n))).sum();
        return Math.min(fuelLow, fuelHigh);
    }

    private static int sumOfConsecutive(int last) {
        return (int)((1.0 + last) / 2 * last);
    }
}
