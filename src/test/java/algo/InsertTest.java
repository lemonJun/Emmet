package algo;

public class InsertTest {

    public void insert(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int tmp = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > tmp) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = tmp;

            for (int ii : data) {
                System.out.print(ii + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int[] data = { 75, 99, 67, 69, 100 };
        new InsertTest().insert(data);
        for (int i : data) {
            System.out.print(i + " ");
        }
    }

}
