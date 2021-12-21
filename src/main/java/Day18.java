public class Day18 {

    static Pairs number;

    public static void main(String[] args) {
        String str = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]";
        int index = 0;
        number = null;
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
                if (number == null) {
                    number = next;
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
        System.out.println();
    }

    private static void add(Pairs p1, Pairs p2) {
        Pairs outer = new Pairs();
        outer.addChild(p1, 0);
        outer.addChild(p2, 1);
    }

    private static void walk(Pairs pairs, int wrap) {
        for (int i = 0; i < 2; i++) {
            if (pairs.getPair()[i] != null) {
                if (wrap >= 4) {
                    explode();
                    return;
                }
                if (pairs.getPair()[i] >= 10) {
                    split();
                    return;
                }
            }
        }
    }

    private static void split() {
    }

    private static void explode() {
    }


}

class Pairs {

    private Pairs parent;
    private Pairs[] child = new Pairs[2];
    private Integer[] pair = new Integer[2];

    public void addChild(Pairs child, int index) {
        this.child[index] = child;
    }

    public void addNumber(int number, int index) {
        this.pair[index] = number;
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
