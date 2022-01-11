import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {

    public static void main(String[] args) {
        String path = "src/main/resources/day21.txt";
        Game game = loadGame(path);
        System.out.println(game.play());
    }

    private static Game loadGame(String path) {
        Pattern p = Pattern.compile("(\\d)$");
        int index = 0;
        int[] pos = new int[2];
        try (Scanner sc = new Scanner(new File(path))){
            while (sc.hasNext()) {
                String line = sc.nextLine();
                Matcher m = p.matcher(line);
                if (m.find()) {
                    pos[index++] = Integer.parseInt(m.group(1));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        return new Game(pos[0], pos[1], 100, 10, 1000);
    }


}

class Game {
    private final Player playerOne;
    private final Player playerTwo;
    private final Die die;
    private final Board board;
    private final int limitOfSuccess;

    public Game(int positionOne, int positionTwo, int sidesOfDie, int spacesOfBoard, int limitOfSuccess) {
        this.playerOne = new Player(positionOne);
        this.playerTwo = new Player(positionTwo);
        this.die = new Die(sidesOfDie);
        this.board = new Board(spacesOfBoard);
        this.limitOfSuccess = limitOfSuccess;
    }

    public int play() {
        Player actual = playerOne;
        while (playerOne.getScore() < limitOfSuccess && playerTwo.getScore() < limitOfSuccess) {
            int distance = die.roll(3);
            actual.move(distance, board.getSpaces());
            actual = actual.equals(playerOne) ? playerTwo : playerOne;
        }
        System.out.println(actual.getScore());
        System.out.println(die.getCounter());
        return actual.getScore() * die.getCounter();
    }
}

class Player {

    private int score;
    private int position;

    public Player(int position) {
        this.position = position;
        this.score = 0;
    }

    public void move(int distance, int spaces) {
        this.position = (this.position + distance) % spaces;
        this.score += this.position == 0 ? 10 : this.position;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

class Die {

    private final int sides;
    private int value;
    private int counter;

    public Die(int sides) {
        this.sides = sides;
        this.value = 1;
        this.counter = 0;
    }

    public int roll() {
        if (value > sides) {
            value = 1;
        }
        counter++;
        return value++;
    }

    public int roll(int num) {
        int sum = 0;
        for (int i = 0; i < num; i++) {
            sum += roll();
        }
        return sum;
    }

    public int getSides() {
        return sides;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

class Board {

    private int spaces;

    public Board(int spaces) {
        this.spaces = spaces;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }
}
