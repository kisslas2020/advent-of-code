import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) {
        List<String> beforePipe = new ArrayList<>();
        List<String> afterPipe = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/day8.txt"))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] linee = line.split("\\s\\|\\s");
                beforePipe.add(linee[0]);
                afterPipe.add(linee[1]);
            }
        } catch (FileNotFoundException e) {

        }
        int res = 0;
        for (String digits : afterPipe) {
            String[] digit = digits.split(" ");
            res += Arrays.stream(digit).filter(d -> d.length() == 2 || d.length() == 3 || d.length() == 4 || d.length() == 7).count();
        }
        System.out.println(res);
        int sum = 0;
        for (int i = 0; i < beforePipe.size(); i++) {
            Digit digit = line(beforePipe.get(i));
            int plus = calculateValue(digit, afterPipe.get(i));
            sum += plus;
        }
        System.out.println(sum);
    }

    private static int calculateValue(Digit digit, String afterPipe) {

        List<Set<Character>> digits = Arrays.stream(afterPipe.split(" "))
                .map(word -> Arrays.stream(word.split("")).map(s -> s.charAt(0)).collect(Collectors.toSet()))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (Set<Character> d : digits) {
            sb.append(digit.findDigit(d));
        }
        return Integer.parseInt(String.valueOf(sb));
    }

    public static Digit line(String beforePipe) {

        List<Set<Character>> digits = Arrays.stream(beforePipe.split(" "))
                .map(word -> Arrays.stream(word.split("")).map(s -> s.charAt(0)).collect(Collectors.toSet()))
                .collect(Collectors.toList());

        Digit digit = new Digit();

        digit.setDigit1(digits.stream().filter(s -> s.size() == 2).findFirst().get());
        digit.setDigit4(digits.stream().filter(s -> s.size() == 4).findFirst().get());
        digit.setDigit7(digits.stream().filter(s -> s.size() == 3).findFirst().get());
        digit.setDigit8(digits.stream().filter(s -> s.size() == 7).findFirst().get());

        digit.setDigit6(digits.stream()
                .filter(s -> s.size() == 6)
                .filter(s6 -> s6.stream().filter(digit.getDigit1()::contains).collect(Collectors.toSet()).size() == 1)
                .findAny().get());
        digit.setDigit9(digits.stream()
                .filter(s -> s.size() == 6)
                .filter(s6 -> s6.stream().filter(digit.getDigit4()::contains).collect(Collectors.toSet()).size() == 4)
                .findAny().get());
        digit.setDigit0(digits.stream()
                .filter(s -> s.size() == 6)
                .filter(s -> s.stream().filter(digit.getDigit4()::contains).collect(Collectors.toSet()).size() == 3)
                .filter(s -> s.stream().filter(digit.getDigit1()::contains).collect(Collectors.toSet()).size() == 2)
                .findAny().get());
        digit.setDigit3(digits.stream()
                .filter(s -> s.size() == 5)
                .filter(s6 -> s6.stream().filter(digit.getDigit7()::contains).collect(Collectors.toSet()).size() == 3)
                .findAny().get());
        digit.setDigit2(digits.stream()
                .filter(s -> s.size() == 5)
                .filter(s6 -> s6.stream().filter(digit.getDigit9()::contains).collect(Collectors.toSet()).size() == 4)
                .findAny().get());
        digit.setDigit5(digits.stream()
                .filter(s -> s.size() == 5)
                .filter(s6 -> s6.stream().filter(digit.getDigit9()::contains).collect(Collectors.toSet()).size() == 5)
                .findAny().get());
        return digit;
    }
}

class Digit {

    Set<Character> digit0;
    Set<Character> digit1;
    Set<Character> digit2;
    Set<Character> digit3;
    Set<Character> digit4;
    Set<Character> digit5;
    Set<Character> digit6;
    Set<Character> digit7;
    Set<Character> digit8;
    Set<Character> digit9;

    public int findDigit(Set<Character> digit) {

        int res;
        switch (digit.size()) {
            case 2:
                res = 1;
                break;
            case 3:
                res = 7;
                break;
            case 4:
                res = 4;
                break;
            case 5:
                if (digit.containsAll(digit2)) {
                    res = 2;
                } else if (digit.containsAll(digit3)) {
                    res = 3;
                } else {
                    res = 5;
                }
                break;
            case 6:
                if (digit.containsAll(digit0)) {
                    res = 0;
                } else if (digit.containsAll(digit6)) {
                    res = 6;
                } else {
                    res = 9;
                }
                break;
            case 7:
                res = 8;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + digit.size());
        }
        return res;
    }

    public Set<Character> getDigit0() {
        return digit0;
    }

    public void setDigit0(Set<Character> digit0) {
        this.digit0 = digit0;
    }

    public Set<Character> getDigit1() {
        return digit1;
    }

    public void setDigit1(Set<Character> digit1) {
        this.digit1 = digit1;
    }

    public Set<Character> getDigit2() {
        return digit2;
    }

    public void setDigit2(Set<Character> digit2) {
        this.digit2 = digit2;
    }

    public Set<Character> getDigit3() {
        return digit3;
    }

    public void setDigit3(Set<Character> digit3) {
        this.digit3 = digit3;
    }

    public Set<Character> getDigit4() {
        return digit4;
    }

    public void setDigit4(Set<Character> digit4) {
        this.digit4 = digit4;
    }

    public Set<Character> getDigit5() {
        return digit5;
    }

    public void setDigit5(Set<Character> digit5) {
        this.digit5 = digit5;
    }

    public Set<Character> getDigit6() {
        return digit6;
    }

    public void setDigit6(Set<Character> digit6) {
        this.digit6 = digit6;
    }

    public Set<Character> getDigit7() {
        return digit7;
    }

    public void setDigit7(Set<Character> digit7) {
        this.digit7 = digit7;
    }

    public Set<Character> getDigit8() {
        return digit8;
    }

    public void setDigit8(Set<Character> digit8) {
        this.digit8 = digit8;
    }

    public Set<Character> getDigit9() {
        return digit9;
    }

    public void setDigit9(Set<Character> digit9) {
        this.digit9 = digit9;
    }
}
