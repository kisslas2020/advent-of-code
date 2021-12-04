import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    @Test
    public void testReadData() {
        Day2 day2 = new Day2();
        day2.readData("day2example.txt");
        assertEquals(900, day2.getX() * day2.getY());
        Day2 day22 = new Day2();
        day22.readData("day2.txt");
        System.out.println(day22.getX() * day22.getY());
    }

}