import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day13 {

    public static void main(String[] args) {
        String path = "src/main/resources/day13example.txt";
        partOne(path);
    }

    private static void partOne(String path) {
        int[][] paper = makePaper(path, measure(path));
        System.out.println("ready");
    }

    private static int[][] measure(String path) {
        int maxX = 0;
        int maxY = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {

                } else if (line.startsWith("fold")) {

                } else {
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    maxX = x > maxX ? x : maxX;
                    maxY = y > maxY ? y : maxY;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[maxY + 1][maxX + 1];
    }

    private static int[][] makePaper(String path, int[][] paper) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {

                } else if (line.startsWith("fold")) {

                } else {
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    paper[y][x] = 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paper;
    }

}

class TransparentPaper {

    private String[] paper;

    public TransparentPaper(String[] paper) {
        this.paper = paper;
    }

    public String[] foldHorizontally(int row) {
        int height = paper.length;
        int width = paper[0].length();
        int lowerHalf = Math.min(row, height - row);
        int upperRowIndex = row - lowerHalf;
        int lowerRowIndex = row + lowerHalf;
        String[] res = new String[height - lowerHalf];

        for (int i = upperRowIndex; i < lowerHalf; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < width; j++) {
                sb.append(paper[i].charAt(j) ^ paper[lowerRowIndex].charAt(j));
            }
            lowerRowIndex--;
            res[i] = String.valueOf(sb);
        }
        return res;
    }
}
