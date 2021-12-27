import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

    private static List<WaterScanner> scanners = new ArrayList<>();

    public static void main(String[] args) {
        String path = "src/main/resources/day19.txt";
        loadScanners(path);
        searchScanners();
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
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
    }

    private static void searchScanners() {
        WaterScanner WSC = scanners.get(0);
        for (int i = 1; i < scanners.size(); i++) {
            WaterScanner w = scanners.get(i);
            Position p = compareScanners(WSC, w);
            if (p != null) {
                w.setPosition(p);
            }
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
                    if (diff.getX() == 68) {
                        System.out.printf("X: %d, Y: %d, Z: %d", diff.getX(), diff.getY(), diff.getZ());
                        System.out.println();
                    }
                    Integer n = distances.getOrDefault(diff, 0);
                    distances.put(diff, n + 1);
                }
            }
        }
        List<Integer> pos = distances.keySet().stream().filter(p -> distances.get(p) > 11).map(p -> distances.get(p)).collect(Collectors.toList());
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
                return new Position(z, y, -x);
            case 2:
                return new Position(x, z, -y);
            case 3:
                return new Position(-z, y, x);
            case 4:
                return new Position(-x, y, -z);
            case 5:
                return new Position(y, -z, y);
            case 6:
                return new Position(y, -x, z);
            case 7:
                return new Position(y, -z, -x);
            case 8:
                return new Position(z, -x, -y);
            case 9:
                return new Position(-y, x, -z);
            case 10:
                return new Position(-y, z, x);
            case 11:
                return new Position(-z, x, y);
            case 12:
                return new Position(-x, -y, z);
            case 13:
                return new Position(-z, -y, -x);
            case 14:
                return new Position(-x, -z, -y);
            case 15:
                return new Position(x, y, -z);
            case 16:
                return new Position(z, y, x);
            case 17:
                return new Position(x, z, y);
            case 18:
                return new Position(-y, x, z);
            case 19:
                return new Position(-y, z, -x);
            case 20:
                return new Position(-z, x, -y);
            case 21:
                return new Position(y, -x, -z);
            case 22:
                return new Position(y, -z, x);
            case 23:
                return new Position(z, -x, y);
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

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
