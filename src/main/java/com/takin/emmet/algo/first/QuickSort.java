package com.takin.emmet.algo.first;

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
    public static int partition(int[] data, int left, int right) {

        return 0;
    }

    public static void main(String[] args) {
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };
        //        int[] retVal = sort(data, 0, 0);
        //        for (int i : retVal) {
        //            System.out.print(i + " ");
        //        }
    }

}
