import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    public void testExample() {
        Day3 day3 = new Day3();
        List<String> lines = day3.readData("day3example.txt");
        int[] aggregator = day3.aggregateLines(lines);
        String gamma = day3.calculateGamma(lines, aggregator);
        String epsilon = day3.calculateEpsilon(gamma);
        int gammaNum = day3.convertBinaryToDecimal(gamma);
        int epsilonNum = day3.convertBinaryToDecimal(epsilon);
        assertEquals(198, gammaNum * epsilonNum);
    }

    @Test
    public void testPart1() {
        Day3 day3 = new Day3();
        List<String> lines = day3.readData("day3.txt");
        int[] aggregator = day3.aggregateLines(lines);
        String gamma = day3.calculateGamma(lines, aggregator);
        String epsilon = day3.calculateEpsilon(gamma);
        int gammaNum = day3.convertBinaryToDecimal(gamma);
        int epsilonNum = day3.convertBinaryToDecimal(epsilon);
        System.out.println(gammaNum * epsilonNum);
    }

    @Test
    public void testExample2() {
        Day3 day3 = new Day3();
        List<String> lines = day3.readData("day3example.txt");
        String oxygen = day3.calculateOxygen(lines);
        String cO2 = day3.calculateCO2(lines);
        System.out.println(oxygen);
        System.out.println(cO2);
        assertEquals(23, day3.convertBinaryToDecimal(oxygen));
        assertEquals(10, day3.convertBinaryToDecimal(cO2));
        System.out.println(day3.convertBinaryToDecimal(oxygen) * day3.convertBinaryToDecimal(cO2));
    }

    @Test
    public void testPart2() {
        Day3 day3 = new Day3();
        List<String> lines = day3.readData("day3.txt");
        String oxygen = day3.calculateOxygen(lines);
        String cO2 = day3.calculateCO2(lines);
        System.out.println(oxygen);
        System.out.println(cO2);
        System.out.println(day3.convertBinaryToDecimal(oxygen) * day3.convertBinaryToDecimal(cO2));
    }
}