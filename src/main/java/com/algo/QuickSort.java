package com.algo;

/**
 * 找一个分界点   左边小于右边
 * http://www.cnblogs.com/coderising/p/5708801.html
 * 
 * @author Administrator
 * @version 1.0
 * @date  2017年7月9日 上午9:52:56
 * @see 
 * @since
 */
public class QuickSort {

    public static void sort(int[] data, int left, int right) {
        if (left < right) {
            int q = partition(data, left, right);
            sort(data, left, q - 1);
            sort(data, q + 1, right);
        }
    }

    /**
     * 分区的过程 把数据按进行交换  同时返回对照数据所在的位置
     * @param data
     * @param left
     * @param right
     * @return
     */
    public static int partition(int[] arrays, int start, int end) {
        int middleValue = arrays[start];
        while (start < end) {
            while (arrays[end] >= middleValue && start < end) {
                end--;
            }
            arrays[start] = arrays[end];
            while (arrays[start] <= middleValue && start < end) {
                start++;
            }
            arrays[end] = arrays[start];
        }
        arrays[start] = middleValue;
        return start;
    }

    public static void main(String[] args) {
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
        System.out.println();
        sort(data, 0, data.length - 1);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
    }

}
