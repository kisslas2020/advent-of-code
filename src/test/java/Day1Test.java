import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {

    @Test
    public void testCountIncrease() {
        Day1 dec01Test = new Day1();
        List<Integer> testList = dec01Test.readData("day1example.txt");

        int result = dec01Test.countIncrease(dec01Test.makeSumsOfThree(testList));
        assertEquals(5, result);
    }
}