import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day20 {

    private static String[] imageEnhancementAlgorithm;
    private static int[][] inputImage;
    private static int[][] outputImage;
    private static int infinitePixel = 0;
    private static int ALGORITHM_MAX_INDEX;

    public static void main(String[] args) {
        String path = "src/main/resources/day20.txt";
        inputImage = new int[measure(path)][];
        loadInput(path);
        repeat(2);
        System.out.println(countOutputImage());
        repeat(48);
        System.out.println(countOutputImage());
    }

    private static void repeat(int number) {
        for (int i = 0; i < number; i++) {
            createOutputImage();
            inputImage = outputImage;
            infinitePixel = toggleInfinitePixel();
        }
    }

    private static int toggleInfinitePixel() {
        String res = infinitePixel == 0 ? imageEnhancementAlgorithm[0] : imageEnhancementAlgorithm[ALGORITHM_MAX_INDEX];
        return res.equals("#") ? 1 : 0;
    }


    private static int measure(String path) {
        int row = 0;
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNext()) {
                sc.nextLine();
                row++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        return row - 2;
    }

    private static void loadInput(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            String line = null;
            int index = 0;
            while (sc.hasNext()) {
                line = sc.nextLine();
                if (imageEnhancementAlgorithm == null) {
                    imageEnhancementAlgorithm = line.split("");
                    ALGORITHM_MAX_INDEX = imageEnhancementAlgorithm.length - 1;
                    continue;
                }
                if (!line.equals("")) {
                    inputImage[index++] = Arrays.stream(line.split("")).mapToInt(n -> {
                        if (n.equals("#")) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }).toArray();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }

    private static void createOutputImage() {
        int row = inputImage.length + 2;
        int col = inputImage[0].length + 2;
        outputImage = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                outputImage[i][j] = calculateValue(i - 1, j - 1);
            }
        }
    }

    private static int calculateValue(int i, int j) {
        int[][] matrixBinary = {{256, 128, 64}, {32, 16, 8}, {4, 2, 1}};
        int[][] matrix3x3 = create3x3(i, j);
        int sum = 0;
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                sum += matrixBinary[k][l] * matrix3x3[k][l];
            }
        }
        return imageEnhancementAlgorithm[sum].equals("#") ? 1 : 0;
    }

    private static int[][] create3x3(int i, int j) {
        int[][] matrix3x3 = new int[3][3];
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (i - 1 + k < 0 || i - 1 + k > inputImage.length - 1 || j - 1 + l < 0 || j - 1 + l > inputImage[0].length - 1) {
                    matrix3x3[k][l] = infinitePixel;
                } else {
                    matrix3x3[k][l] = inputImage[i - 1 + k][j - 1 + l];
                }
            }
        }
        return matrix3x3;
    }

    private static int countOutputImage() {
        int sum = 0;
        for (int i = 0; i < outputImage.length; i++) {
            for (int j = 0; j < outputImage[0].length; j++) {
                sum += outputImage[i][j];
            }
        }
        return sum;
    }

    private static void printImage(int[][] outputImage) {
        for (int i = 0; i < outputImage.length; i++) {
            for (int j = 0; j < outputImage[0].length; j++) {
                String value = outputImage[i][j] == 1 ? "#" : ".";
                System.out.print(value);
            }
            System.out.println();
        }
    }

}
