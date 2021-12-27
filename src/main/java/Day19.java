import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day19 {

    private static List<WaterScanner> scanners = new ArrayList<>();

    public static void main(String[] args) {
        String path = "src/main/resources/day19.txt";
        loadScanners(path);
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
        System.out.println("ready");
    }

    private static boolean search12(WaterScanner wsc) {

    }

    private static WaterScanner rotate(int index) {

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

    private final int y;
    private final int x;
    private final int z;

    public Position(int y, int x, int z) {
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
