package algo;

public class QuickSortTest {

    public void sort(int[] data, int start, int end) {
        if (start < end) {
            int partition = partition(data, start, end);
            sort(data, start, partition - 1);
            sort(data, partition + 1, end);
        }
    }

    public int partition(int[] data, int start, int end) {
        int tmp = data[start];
        while (start < end) {
            while (data[end] >= tmp && start < end) {
                end--;
            }
            data[start] = data[end];
            while (data[start] <= tmp && start < end) {
                start++;
            }
            data[end] = data[start];
        }
        data[start] = tmp;//放置中位数
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
        System.out.println();
        return start;
    }

    public static void main(String[] args) {
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
        System.out.println();
        new QuickSortTest().sort(data, 0, data.length - 1);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
    }
}
