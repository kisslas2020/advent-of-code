import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) {
        Game game = new Game("day4.txt");
        System.out.println(game.playForWin());
        System.out.println(game.playForDefeat());
    }

    private static class Game {

        private int[] drawnNumbers;
        private List<Bingo> boards = new ArrayList<>();
        private int side;

        public Game(String path) {
            init(path);
        }

        private void init(String path) {
            try (Scanner sc = new Scanner(new File(path))) {
                drawnNumbers = convertToInt(sc.nextLine().split(","));
                while (sc.hasNext()) {
                    sc.nextLine();
                    String l = sc.nextLine().trim();
                    int[] row = convertToInt(l.split(" ++"));
                    side = row.length;
                    Bingo bingo = new Bingo(side);
                    bingo.getBoard()[0] = row;
                    for (int i = 1; i < side; i++) {
                        String line = sc.nextLine().trim();
                        row = convertToInt(line.split(" ++"));
                        bingo.getBoard()[i] = row;
                    }
                    boards.add(bingo);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + path);
            }
        }

        public int playForWin() {
            for (int number : drawnNumbers) {
                for (Bingo bingo : boards) {
                    bingo.drawANumber(number);
                    if (bingo.isWinningBoard()) {
                        return countFinalScore(bingo, number);
                    }
                }
            }
            return 0;
        }

        public int playForDefeat() {
            int numberOfBoards = boards.size();
            for (int number : drawnNumbers) {
                Bingo last = boards.get(0);
                boards = boards.stream().filter(bingo -> {
                    bingo.drawANumber(number);
                    return !bingo.isWinningBoard();
                }).collect(Collectors.toList());
                if (boards.size() == 0) {
                    return countFinalScore(last, number);
                }

            }
            return 0;
        }

        private int countFinalScore(Bingo bingo, int number) {
            int score = createSumOfItems(bingo.getBoard()) - createSumOfItems(bingo.getMarkedNumbers());
            return score * number;
        }

        private int createSumOfItems(int[][] array) {
            int sum = 0;
            int n = array.length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sum += array[i][j];
                }
            }
            return sum;
        }

        private int[] convertToInt(String[] array) {
            int n = array.length;
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = Integer.parseInt(array[i]);
            }
            return res;
        }
    }

    private static class Bingo {

        private int[][] board;
        private int[][] markedNumbers;

        public Bingo(int side) {
            board = new int[side][side];
            markedNumbers = new int[side][side];
        }

        public void drawANumber(int number) {
            int side = board.length;
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    int boardNumber = board[i][j];
                    if (boardNumber == number) {
                        markedNumbers[i][j] = number;
                        return;
                    }
                }
            }
        }

        public boolean isWinningBoard() {
            return examineRows() || examineColumns();
        }

        private boolean examineRows() {
            int side = board.length;
            outer:
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (markedNumbers[i][j] != board[i][j]) {
                        continue outer;
                    }
                }
                return true;
            }
            return false;
        }

        private boolean examineColumns() {
            int side = board.length;
            outer:
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (markedNumbers[j][i] != board[j][i]) {
                        continue outer;
                    }
                }
                return true;
            }
            return false;
        }

        public int[][] getBoard() {
            return board;
        }

        public void setBoard(int[][] board) {
            this.board = board;
        }

        public int[][] getMarkedNumbers() {
            return markedNumbers;
        }

        public void setMarkedNumbers(int[][] markedNumbers) {
            this.markedNumbers = markedNumbers;
        }
    }


}
