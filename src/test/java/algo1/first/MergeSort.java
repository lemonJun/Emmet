package algo1.first;

public class MergeSort {
    public static void main(String[] args) {
        int[] data = new int[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 };
        print(data);
        sort(data, 0, data.length - 1);
        System.out.println("排序后的数组：");
        print(data);
    }

    public static void sort(int[] data, int left, int right) {
        if (left >= right)
            return;
        // 找出中间索引  
        int center = (left + right) / 2;
        // 对左边数组进行递归  
        sort(data, left, center);
        // 对右边数组进行递归  
        sort(data, center + 1, right);
        // 合并  
        merge(data, left, center, right);
        print(data);
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] tmpArr = new int[data.length];
        int mid = center + 1;
        int begin = left;
        int tmp = left;
        while (left <= center && mid <= right) {
            if (data[left] <= data[mid]) {
                tmpArr[begin++] = data[left++];
            } else {
                tmpArr[begin++] = data[mid++];
            }
        }
        while (mid <= right) {
            tmpArr[begin++] = data[mid++];
        }
        while (left <= center) {
            tmpArr[begin++] = data[left++];
        }
        while (tmp <= right) {
            data[tmp] = tmpArr[tmp++];
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }

}
