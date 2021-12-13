import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    public static void main(String[] args) {
        String path = "src/main/resources/day13.txt";
        origami(path);
    }

    private static void origami(String path) {
        List<String> foldOperations = new ArrayList<>();
        int[][] paper = makePaper(path, measure(path), foldOperations);
        TransparentPaper transparentPaper = new TransparentPaper(paper);
        for (String operation : foldOperations) {
            String dir = findDirection(operation);
            int number = findNumber(operation);
            if (dir.equals("y")) {
                transparentPaper = new TransparentPaper(transparentPaper.foldHorizontally(number));
            } else {
                transparentPaper = new TransparentPaper(transparentPaper.foldVertically(number));
            }
            System.out.println(countDots(transparentPaper));
        }
        print(transparentPaper);
    }

    private static void print(TransparentPaper transparentPaper) {
        int[][] result = transparentPaper.getPaper();
        for (int[] row : result) {
            for (int i : row) {
                System.out.print(i == 0 ? '.' : '#');
            }
            System.out.println();
        }
    }

    private static int countDots(TransparentPaper transparentPaper) {
        return Arrays.stream(transparentPaper.getPaper()).map(row -> Arrays.stream(row).sum()).mapToInt(n -> n).sum();
    }

    private static String findDirection(String operation) {
        Pattern pDir = Pattern.compile("(\\w)=");
        Matcher mDir = pDir.matcher(operation);
        if (mDir.find()) {
            return mDir.group(1);
        }
        return null;
    }

    private static int findNumber(String operation) {
        Pattern pNum = Pattern.compile("=(\\d+)");
        Matcher mNum = pNum.matcher(operation);
        if (mNum.find()) {
            return Integer.parseInt(mNum.group(1));
        }
        return 0;
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

    private static int[][] makePaper(String path, int[][] paper, List<String> foldOperations) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("")) {

                } else if (line.startsWith("fold")) {
                    foldOperations.add(line);
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

    private int[][] paper;

    public TransparentPaper(int[][] paper) {
        this.paper = paper;
    }

    public int[][] foldHorizontally(int row) {
        int height = paper.length;
        int width = paper[0].length;
        int lowerHalf = Math.min(row, height - row - 1);
        int upperRowIndex = row - lowerHalf;
        int lowerRowIndex = row + lowerHalf;
        int[][] foldedPaper = new int[height - lowerHalf - 1][width];
        int topRows = height - (2 * lowerHalf + 1);

        for (int i = upperRowIndex; i < lowerHalf; i++) {
            for (int j = 0; j < width; j++) {
                foldedPaper[i + topRows][j] = paper[i][j] | paper[lowerRowIndex][j];
            }
            lowerRowIndex--;
        }
        if (topRows != 0) {
            int start = upperRowIndex == 0 ? height - 1 : 0;
            foldedPaper = copyMissingRows(foldedPaper, topRows, start);
        }
        return foldedPaper;
    }

    private int[][] copyMissingRows(int[][] foldedPaper, int topRows, int start) {
        if (start == 0) {
            for (int i = 0; i < topRows; i++) {
                for (int j = 0; j < foldedPaper[0].length; j++) {
                    foldedPaper[i][j] = paper[i][j];
                }
            }
        } else {
            for (int i = 0; i < topRows; i++) {
                for (int j = 0; j < foldedPaper.length; j++) {
                    foldedPaper[i][j] = paper[start][j];
                }
                start--;
            }
        }
        return foldedPaper;
    }

    public int[][] foldVertically(int column) {
        int height = paper.length;
        int width = paper[0].length;
        int slimmerHalf = Math.min(column, width - column - 1);
        int leftColumnIndex = column - slimmerHalf;
        int[][] foldedPaper = new int[height][width - slimmerHalf - 1];
        int leftColumns = width - (2 * slimmerHalf + 1);

        for (int i = 0; i < height; i++) {
            int rightColumnIndex = column + slimmerHalf;
            for (int j = leftColumnIndex; j < slimmerHalf; j++) {
                foldedPaper[i][j] = paper[i][j] | paper[i][rightColumnIndex--];
            }
        }
        if (leftColumns != 0) {
            int start = leftColumnIndex == 0 ? width - 1 : 0;
            foldedPaper = copyMissingColumns(foldedPaper, leftColumns, start);
        }
        return foldedPaper;
    }

    private int[][] copyMissingColumns(int[][] foldedPaper, int leftColumns, int start) {
        if (start == 0) {
            for (int i = 0; i < foldedPaper.length; i++) {
                for (int j = 0; j < leftColumns; j++) {
                    foldedPaper[i][j] = paper[i][j];
                }
            }
        } else {
            for (int i = 0; i < foldedPaper.length; i++) {
                int index = start;
                for (int j = 0; j < leftColumns; j++) {
                    foldedPaper[i][j] = paper[i][index--];
                }
            }
        }
        return foldedPaper;
    }

    public int[][] getPaper() {
        return paper;
    }

    public void setPaper(int[][] paper) {
        this.paper = paper;
    }
}
