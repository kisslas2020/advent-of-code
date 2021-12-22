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
                wsc.addBeacon(new Beacon(line.split(",")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        System.out.println("ready");
    }


}

class WaterScanner {

    private int x;
    private int y;
    private int z;
    private Set<Beacon> beacons = new HashSet<>();


    public void addBeacon(Beacon beacon) {
        beacons.add(beacon);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }
}

class Beacon {

    private int x;
    private int y;
    private int z;

    public Beacon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Beacon(String[] line) {
        this(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beacon beacon = (Beacon) o;
        return x == beacon.x && y == beacon.y && z == beacon.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
