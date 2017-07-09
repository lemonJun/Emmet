package algo;

public class PermutaTest {

    public static void permu(String row, String result, int len) {
        if (result.length() == len) {
            System.out.println(result);
        } else {
            for (int i = 0; i < row.length(); i++) {
                if (result.indexOf(row.charAt(i)) < 0) {
                    permu(row, result + row.charAt(i), len);
                }
            }
        }
    }

    public static void main(String[] args) {
        String str = "abcd";
        permu(str, "", 3);
    }
}
