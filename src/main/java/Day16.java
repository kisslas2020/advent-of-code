import java.io.*;
import java.util.*;

public class Day16 {

    private static final Map<String, String> hexadecimalToBinaryMap = new HashMap<>();
    private static String messageBinary;
    private static Packet outerPacket;
    private static int index = 0;

    public static void main(String[] args) {
        String path = "src/main/resources/day16.txt";
        initMap();
        messageBinary = readMessage(path);
        outerPacket = nextPacket();
        int sum = calculateSumOfVersions(outerPacket, 0);

        System.out.println(sum);
    }

    private static void initMap() {
        hexadecimalToBinaryMap.put("0", "0000");
        hexadecimalToBinaryMap.put("1", "0001");
        hexadecimalToBinaryMap.put("2", "0010");
        hexadecimalToBinaryMap.put("3", "0011");
        hexadecimalToBinaryMap.put("4", "0100");
        hexadecimalToBinaryMap.put("5", "0101");
        hexadecimalToBinaryMap.put("6", "0110");
        hexadecimalToBinaryMap.put("7", "0111");
        hexadecimalToBinaryMap.put("8", "1000");
        hexadecimalToBinaryMap.put("9", "1001");
        hexadecimalToBinaryMap.put("A", "1010");
        hexadecimalToBinaryMap.put("B", "1011");
        hexadecimalToBinaryMap.put("C", "1100");
        hexadecimalToBinaryMap.put("D", "1101");
        hexadecimalToBinaryMap.put("E", "1110");
        hexadecimalToBinaryMap.put("F", "1111");
    }

    private static String readMessage(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int line;
            while ((line = reader.read()) != -1) {
                char c = (char) line;
                String binary = hexadecimalToBinaryMap.get("" + c);
                sb.append(binary);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(sb);
    }

    private static void extractLiteralPacket(Packet packet) {
        StringBuilder sb = new StringBuilder();
        int prefix = 0;
        do {
            prefix = Integer.parseInt(messageBinary.substring(index, index + 1));
            sb.append(messageBinary.substring(index + 1, index + 5));
            index += 5;
        } while (prefix != 0);
        packet.setLiteralValue(binaryToDecimal(String.valueOf(sb)));
    }

    private static void extractOperatorPacket(Packet packet) {
        int lengthTypeID = Integer.parseInt(messageBinary.substring(index, index + 1));
        index++;
        if (lengthTypeID == 0) {
            int length = binaryToDecimal(messageBinary.substring(index, index + 15));
            index += 15;
            int limit = index + length;
            while (limit > index) {
                packet.addSubPacket(nextPacket());
            }
        } else if (lengthTypeID == 1) {
            int number = binaryToDecimal(messageBinary.substring(index, index + 11));
            index += 11;
            for (int i = 0; i < number; i++) {
                packet.addSubPacket(nextPacket());
            }
        }
    }

    private static Packet nextPacket() {
        int version = binaryToDecimal(messageBinary.substring(index, index + 3));
        int typeID = binaryToDecimal(messageBinary.substring(index + 3, index + 6));
        index += 6;
        Packet newPacket = new Packet(version, typeID);
        if (typeID == 4) {
            extractLiteralPacket(newPacket);
        } else {
            extractOperatorPacket(newPacket);
        }
        return newPacket;
    }

    private static int calculateSumOfVersions(Packet packet, int sum) {
        for (Packet p : packet.getPackets()) {
            sum = calculateSumOfVersions(p, sum);
        }
        return sum + packet.getVersion();
    }

    private static int binaryToDecimal(String binary) {
        String num = binary;
        int decimal = 0;
        int base = 1;
        int length = num.length();
        for (int i = length - 1; i >= 0; i--) {
            if (num.charAt(i) == '1') {
                decimal += base;
            }
            base = base * 2;
        }
        return decimal;
    }
}

class Packet {

    private final int version;
    private final int typeId;
    private int literalValue;
    private final List<Packet> packets = new ArrayList<>();

    public Packet(int version, int typeId) {
        this.version = version;
        this.typeId = typeId;
    }

    public int getVersion() {
        return version;
    }

    public int getTypeId() {
        return typeId;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void addSubPacket(Packet subPacket) {
        packets.add(subPacket);
    }

    public int getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(int literalValue) {
        this.literalValue = literalValue;
    }
}


