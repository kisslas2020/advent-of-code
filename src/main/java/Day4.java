import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {

    public static void main(String[] args) {
        Game game = new Game("day4.txt");
        game.play();
    }

    private static class Game {

        private String[] drawnNumbers;
        private List<Bingo> boards = new ArrayList<>();
        private int side;

        public Game(String path) {
            init(path);
        }

        private void init(String path) {
            try (Scanner sc = new Scanner(new File(path))) {
                drawnNumbers = sc.nextLine().split(",");
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

        public int play() {
            return 0;
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
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (markedNumbers[i][j] == 0) {
                        break;
                    }
                }
                return true;
            }
            return false;
        }

        private boolean examineColumns() {
            int side = board.length;
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (markedNumbers[j][i] == 0) {
                        break;
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
