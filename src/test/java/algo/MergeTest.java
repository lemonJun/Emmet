package algo;

public class MergeTest {

    public static void main(String[] args) {
        int[] data = new int[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 };
        print(data);
        sort(data, 0, data.length - 1);
        System.out.println("排序后的数组：");
        print(data);
    }

    public static void sort(int[] data, int begin, int end) {
        if (begin >= end) {
            return;
        }

        int mid = (begin + end) / 2;
        sort(data, begin, mid);
        sort(data, mid + 1, end);
        merge(data, begin, mid, end);
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] tmpAry = new int[data.length];
        int mid = center + 1;
        int begin = left;
        int tmp = left;
        while (left <= center && mid <= right) {
            if (data[left] <= data[mid]) {
                tmpAry[begin++] = data[left++];
            } else {
                tmpAry[begin++] = data[mid++];
            }
        }
        while (mid <= right) {
            tmpAry[begin++] = data[mid++];
        }
        while (left <= center) {
            tmpAry[begin++] = data[left++];
        }
        while (tmp <= right) {
            data[tmp] = tmpAry[tmp++];
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }
}
