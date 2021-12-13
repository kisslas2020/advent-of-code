public class Day13 {


}

class TransparentPaper {

    private String[] paper;

    public TransparentPaper(String[] paper) {
        this.paper = paper;
    }

    public String[] foldHorizontally(int row) {
        int height = paper.length;
        int width = paper[0].length();
        int lowerHalf = Math.min(row, height - row);
        int upperRowIndex = row - lowerHalf;
        int lowerRowIndex = row + lowerHalf;
        String[] res = new String[height - lowerHalf];

        for (int i = upperRowIndex; i < lowerHalf; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < width; j++) {
                sb.append(paper[i].charAt(j) ^ paper[lowerRowIndex].charAt(j));
            }
            lowerRowIndex--;
            res[i] = String.valueOf(sb);
        }
        return res;
    }
}
