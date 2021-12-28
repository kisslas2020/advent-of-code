import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

    private static List<WaterScanner> scanners = new ArrayList<>();
    private static List<Position> positions = new ArrayList<>();

    public static void main(String[] args) {
        String path = "src/main/resources/day19.txt";
        loadScanners(path);
        searchScanners();
        System.out.println(scanners.get(0).getBeacons().size());
        System.out.println(calculateManhattanDistance());
    }

    private static int calculateManhattanDistance() {
        int maxDistance = 0;
        Position[] res = new Position[2];
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Position p1 = positions.get(i);
                Position p2 = positions.get(j);
                int d = Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()) + Math.abs(p1.getZ() - p2.getZ());
                if (d > maxDistance) {
                    maxDistance = d;
                    res[0] = p1;
                    res[1] = p2;
                }
            }
        }
        return maxDistance;
    }

    private static void loadScanners(String path) {
        try (Scanner sc = new Scanner(new File(path))){
            WaterScanner wsc = null;
            while(sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("Scanner")) {
                    wsc = new WaterScanner();
                    continue;
                }
                if (line.equals("")) {
                    scanners.add(wsc);
                    continue;
                }
                wsc.addBeacon(new Beacon(new Position(line.split(","))));
            }
            scanners.add(wsc);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }

    private static void searchScanners() {
        WaterScanner WSC = scanners.get(0);
        positions.add(new Position(0, 0, 0));
        int i = 1;
        while (scanners.size() > 1) {
            System.out.println(scanners.size() + " / " + i);
            if (i > scanners.size() - 1) {
                i = 1;
            }
            WaterScanner w = scanners.get(i++);
            Position p = compareScanners(WSC, w);
            if (p != null) {
                w.setPosition(p);
                mergeScanner(WSC, w);
                positions.add(p);
                scanners.remove(w);
                i = 1;
            }
        }
    }

    private static void mergeScanner(WaterScanner WSC, WaterScanner w) {
        for (Beacon beacon : w.getBeacons()) {
            Position p = beacon.getPosition();
            Position sc = w.getPosition();
            int i = w.getPosition().getI();
            Position pRotated = rotate(i, p.getX(), p.getY(), p.getZ());
            int x = pRotated.getX() + sc.getX();
            int y = pRotated.getY() + sc.getY();
            int z = pRotated.getZ() + sc.getZ();
            Position pAbsolute = new Position(x, y, z);
            WSC.addBeacon(new Beacon(pAbsolute));
        }
    }

    private static Position compareScanners(WaterScanner WSC, WaterScanner w) {
        Map<Position, Integer> distances = new HashMap<>();
        for (Beacon BEACON : WSC.getBeacons()) {
            Position pBEACON = BEACON.getPosition();
            for (Beacon wBeacon : w.getBeacons()) {
                Position original = wBeacon.getPosition();
                for (int i = 0; i < 24; i++) {
                    Position rotated = rotate(i, original.getX(), original.getY(), original.getZ());
                    Position diff = distance(pBEACON, rotated);
                    diff.setI(i);
                    Integer n = distances.getOrDefault(diff, 0);
                    if (n == 11) {
                        return diff;
                    }
                    distances.put(diff, n + 1);
                }
            }
        }
        Map<Position, Integer> m = distances.entrySet().stream().filter(e -> e.getValue() > 1).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        Position res = null;
        for (Position p : distances.keySet()) {
            if (distances.get(p) >= 12) {
                res = p;
            }
        }
        return res;
    }

    private static Position rotate(int num, int x, int y, int z) {
        switch (num) {
            case 0:
                return new Position(x, y, z);
            case 1:
                return new Position(-y, x, z);
            case 2:
                return new Position(-x, -y, z);
            case 3:
                return new Position(y, -x, z);
            case 4:
                return new Position(-x, y, -z);
            case 5:
                return new Position(-y, -x, -z);
            case 6:
                return new Position(x, -y, -z);
            case 7:
                return new Position(y, x, -z);
            case 8:
                return new Position(z, y, -x);
            case 9:
                return new Position(-y, z, -x);
            case 10:
                return new Position(-z, -y, -x);
            case 11:
                return new Position(y, -z, -x);
            case 12:
                return new Position(-z, y, x);
            case 13:
                return new Position(-y, -z, x);
            case 14:
                return new Position(z, -y, x);
            case 15:
                return new Position(y, z, x);
            case 16:
                return new Position(x, z, -y);
            case 17:
                return new Position(-z, x, -y);
            case 18:
                return new Position(-x, -z, -y);
            case 19:
                return new Position(z, -x, -y);
            case 20:
                return new Position(x, -z, y);
            case 21:
                return new Position(z, x, y);
            case 22:
                return new Position(-x, z, y);
            case 23:
                return new Position(-z, -x, y);
            default:
                return null;
        }
    }

    private static Position distance(Position pBEACON, Position rotated) {
        int x = pBEACON.getX() - rotated.getX();
        int y = pBEACON.getY() - rotated.getY();
        int z = pBEACON.getZ() - rotated.getZ();
        return new Position(x, y, z);
    }

}

class WaterScanner {

    private Position position;
    private Set<Beacon> beacons = new HashSet<>();

    public void addBeacon(Beacon beacon) {
        beacons.add(beacon);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterScanner that = (WaterScanner) o;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}

class Beacon {

    private Position position;

    public Beacon(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beacon beacon = (Beacon) o;
        return position.equals(beacon.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}

class Position {

    private final int x;
    private final int y;
    private final int z;
    private int i;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(int x, int y, int z, int i) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.i = i;
    }

    public Position(String[] line) {
        this(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y && z == position.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
