import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Day5 {

    public static void main(String[] args) {
        ReviewVents reviewVents = new ReviewVents("day5.txt");
        System.out.println(reviewVents.review());
    }

    private static class ReviewVents {

        private Map<Vent, Integer> vents = new HashMap<>();
        private String path;

        public ReviewVents(String path) {
            this.path = path;
        }

        public int review() {
            readData();
            return countOverlaps();
        }

        private int countOverlaps() {
            return (int) vents.values().stream().filter(n -> n > 1).count();
        }

        public void readData() {
            try (Scanner sc = new Scanner(new File(path))) {
                while (sc.hasNext()) {
                    String[] data = sc.nextLine().split(" -> ");
                    int x1 = Integer.parseInt(data[0].split(",")[0]);
                    int y1 = Integer.parseInt(data[0].split(",")[1]);
                    int x2 = Integer.parseInt(data[1].split(",")[0]);
                    int y2 = Integer.parseInt(data[1].split(",")[1]);
                    separateByDirection(x1, y1, x2, y2);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + path);
            }
        }

        private void separateByDirection(int x1, int y1, int x2, int y2) {
            if (x1 == x2) {
                horizontal(x1, y1, y2);
            } else if (y1 == y2) {
                vertical(x1, y1, x2);
            } else if ((x1 - x2) == (y1 - y2)) {
                fromTopToBottom(x1, y1, x2, y2);
            } else if ((x2 - x1) == (y1 - y2)) {
                fromBottomToTop(x1, y1, x2, y2);
            }
        }

        private void fromBottomToTop(int x1, int y1, int x2, int y2) {
            if (x1 > x2) {
                int tempX = x1;
                x1 = x2;
                x2 = tempX;
                int tempY = y1;
                y1 = y2;
                y2 = tempY;
            }
            int len = x2 - x1 + 1;
            for (int i = 0; i < len; i++) {
                Vent vent = new Vent(x1 + i, y1 - i);
                int value = vents.containsKey(vent) ? vents.get(vent) : 0;
                vents.put(vent, value + 1);
            }
        }

        private void fromTopToBottom(int x1, int y1, int x2, int y2) {
            if (x1 > x2) {
                int tempX = x1;
                x1 = x2;
                x2 = tempX;
                int tempY = y1;
                y1 = y2;
                y2 = tempY;
            }
            int len = x2 - x1 + 1;
            for (int i = 0; i < len; i++) {
                Vent vent = new Vent(x1 + i, y1 + i);
                int value = vents.containsKey(vent) ? vents.get(vent) : 0;
                vents.put(vent, value + 1);
            }
        }

        private void vertical(int x1, int y1, int x2) {
            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            for (int i = x1; i <= x2; i++) {
                Vent vent = new Vent(i, y1);
                int value = vents.containsKey(vent) ? vents.get(vent) : 0;
                vents.put(vent, value + 1);
            }
        }

        private void horizontal(int x1, int y1, int y2) {
            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int i = y1; i <= y2; i++) {
                Vent vent = new Vent(x1, i);
                int value = vents.containsKey(vent) ? vents.get(vent) : 0;
                vents.put(vent, value + 1);
            }
        }

    }

    private static class Vent {

        private int x;
        private int y;

        public Vent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vent vent = (Vent) o;
            return x == vent.x && y == vent.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
