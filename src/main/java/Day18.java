import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18 {

    static Pairs number;

    public static void main(String[] args) {
        String path = "src/main/resources/day18.txt";
        System.out.println(partTwo(path));
    }

    private static int partOne(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            number = createPairs(sc.nextLine());
            while (sc.hasNext()) {
                String s = sc.nextLine();
                add(createPairs(s));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        System.out.println(String.valueOf(print(number, new StringBuilder())));
        return number.calculateMagnitude();
    }

    private static int partTwo(String path) {
        List<String> allPairs = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNext()) {
                allPairs.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < allPairs.size(); i++) {
            for (int j = 0; j < allPairs.size(); j++) {
                if (i == j) {
                    continue;
                }
                String sum = "[" + allPairs.get(i) + "," + allPairs.get(j) + "]";
                Pairs p = createPairs(sum);
                int spl = -1;
                while (spl < 0) {
                    int res = -1;
                    while (res < 0) {
                        res = reduce(p, 0);
                    }
                    spl = splitOne(p);
                }
                int m = p.calculateMagnitude();
                if (m > max) {
                    max = m;
                }
            }
        }
        return max;
    }

    private static Pairs createPairs(String str) {
        int index = 0;
        Pairs res = null;
        Pairs current = null;
        for (int i = 0; i < str.length(); i++) {
            String c = str.substring(i, i + 1);
            if (c.matches("\\d")) {
                current.addNumber(Integer.parseInt(c), index);
                index++;
            } else if (c.matches(",")) {
                index = 1;
            } else if (c.matches("\\[")) {
                Pairs next = new Pairs();
                if (res == null) {
                    res = next;
                    current = next;
                } else {
                    current.addChild(next, index);
                    next.setParent(current);
                    current = next;
                }
                index = 0;
            } else if (c.matches("\\]")) {
                current = current.getParent();
            }
        }
        return res;
    }

    private static void add(Pairs p) {
        Pairs newNumber = new Pairs();
        newNumber.addChild(number, 0);
        newNumber.addChild(p, 1);
        number.setParent(newNumber);
        p.setParent(newNumber);
        number = newNumber;
        int spl = -1;
        while (spl < 0) {
            int res = -1;
            while (res < 0) {
                res = reduce(number, 0);
            }
            spl = splitOne(number);
        }
    }



    private static StringBuilder print(Pairs pairs, StringBuilder sb) {
        sb.append("[");
        for (int i = 0; i < 2; i++) {
            if (pairs.getPair()[i] != null) {
                sb.append(pairs.getPair()[i]);
            } else {
                print(pairs.getChild()[i], sb);
            }
            if (i == 0) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb;
    }

    private static int reduce(Pairs pairs, int wrap) {
        wrap++;
        if (wrap > 4) {
            pairs.getParent().explode(pairs);
            return -1;
        }
        for (int i = 0; i < 2; i++) {
            if (pairs.getChild()[i] != null) {
                int res = reduce(pairs.getChild()[i], wrap);
                if (res == -1) {
                    return -1;
                }
            }
        }
        return 1;
    }

    private static int splitOne(Pairs pairs) {
        for (int i = 0; i < 2; i++) {
            if (pairs.getPair()[i] != null) {
                if (pairs.getPair()[i] >= 10) {
                    pairs.split(i);
                    return -1;
                }
            } else {
                int res = splitOne(pairs.getChild()[i]);
                if (res == -1) {
                    return -1;
                }
            }
        }
        return 1;
    }

}

class Pairs {

    private Pairs parent;
    private Pairs[] child = new Pairs[2];
    private Integer[] pair = new Integer[2];

    public void addChild(Pairs child, int index) {
        this.child[index] = child;
    }

    public void addNumber(Integer number, int index) {
        this.pair[index] = number;
    }

    public void explode(Pairs from) {
        int index = this.child[0] == from ? 0 : 1;
        int n0 = from.getPair()[0];
        int n1 = from.getPair()[1];
        this.addFirst(n0, from);
        this.addSecond(n1, from);
        this.addChild(null, index);
        this.addNumber(0, index);
    }

    private void addFirst(int n0, Pairs from) {
        if (this.child[0] == from && this.parent != null) {
            this.parent.addFirst(n0, this);
        } else if (this.child[1] == from){
            if (this.pair[0] != null) {
                this.pair[0] += n0;
            } else {
                this.child[0].addFirst(n0, this);
            }
        } else if (this.parent == from) {
            if (this.pair[1] != null) {
                this.pair[1] += n0;
            } else {
                this.child[1].addFirst(n0, this);
            }
        }
    }

    private void addSecond(int n1, Pairs from) {
        if (this.child[1] == from && this.parent != null) {
            this.parent.addSecond(n1, this);
        } else if (this.child[0] == from) {
            if (this.pair[1] != null) {
                this.pair[1] += n1;
            } else {
                this.child[1].addSecond(n1, this);
            }
        } else if (this.parent == from) {
            if (this.pair[0] != null) {
                this.pair[0] += n1;
            } else {
                this.child[0].addSecond(n1, this);
            }
        }
    }

    public void split(int index) {
        Pairs p = new Pairs();
        int n = this.pair[index];
        int n0 = n / 2;
        int n1 = n - n0;
        p.addNumber(n0, 0);
        p.addNumber(n1, 1);
        p.setParent(this);
        this.addNumber(null, index);
        this.addChild(p, index);
    }

    public int calculateMagnitude() {
        int m0 = 0;
        int m1 = 0;
        m0 = this.pair[0] == null ? this.child[0].calculateMagnitude() : this.pair[0];
        m1 = this.pair[1] == null ? this.child[1].calculateMagnitude() : this.pair[1];
        return 3 * m0 + 2 * m1;
    }



    public Pairs[] getChild() {
        return child;
    }

    public void setChild(Pairs[] child) {
        this.child = child;
    }

    public Integer[] getPair() {
        return pair;
    }

    public void setPair(Integer[] pair) {
        this.pair = pair;
    }

    public Pairs getParent() {
        return parent;
    }

    public void setParent(Pairs parent) {
        this.parent = parent;
    }
}
