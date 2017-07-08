package com.algo.first;

public class MergeSort {

    /**
     * 1 将含有n个数据的表，看成n个长度为1的有序表，然后两两合并，得到[n/2]个长度为2/1的表，再两两合并
     * 2 不是原址排序的   需要用到n的空间
     * 3 
     * @param data  
     * @return  
     */
    public static void sort(int[] data, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sort(data, left, center);
            sort(data, center + 1, right);
            merge(data, left, center, right);
        }

        for (int i : data) {
            System.out.print(i + " ");
        }
    }

    public static void merge(int[] data, int left, int center, int right) {
        int[] array = new int[data.length];
        int mid = center + 1;
        int begin = left;
        while (left <= right && mid <= right) {
            if (data[left] <= data[mid]) {
                array[begin++] = data[left++];
            } else {
                array[begin++] = data[mid++];
            }
        }
        while (left <= center) {
            array[begin++] = data[left++];
        }
        while (mid <= right) {
            array[begin++] = data[mid++];
        }

        int tmp = left;
        while (tmp <= right) {
            data[tmp] = array[tmp++];
        }
    }

    public static void main(String[] args) {
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };

        sort(data, 0, data.length - 1);

    }

}
