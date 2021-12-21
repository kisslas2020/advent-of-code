public class Day18 {

    static Pairs number;

    public static void main(String[] args) {
        String str = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]";
        number = createPairs(str);
        walk(number, 0);
        int mag = number.calculateMagnitude();
        System.out.println();
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
        number = newNumber;
    }

    private static int walk(Pairs pairs, int wrap) {
        wrap++;
        if (wrap > 4) {
            pairs.explode();
            return - 1;
        }
        for (int i = 0; i < 2; i++) {
            if (pairs.getPair()[i] != null) {
                if (pairs.getPair()[i] >= 10) {
                    pairs.split(i);
                    return - 1;
                }
            } else {
                int res = walk(pairs.getChild()[i], wrap);
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

    public void explode() {
        int n0 = this.pair[0];
        int n1 = this.pair[1];

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
