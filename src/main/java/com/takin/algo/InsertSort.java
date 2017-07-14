package com.takin.algo;

//向有序集合中  在正确的位置  插入数据
public class InsertSort {

    /**
     * 第二层j在进行比较时 是从后向前还是从前向后？  从后向前
     * 可以看出插入排序是原址的
     * @param data
     * @return
     */
    public static int[] run(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;

            for (int ii : data) {
                System.out.print(ii + " ");
            }
            System.out.println();
        }
        return data;
    }

    public static void main(String[] args) {

        int[] data = { 75, 99, 67, 69, 100 };
        int[] retVal = run(data);
        for (int i : retVal) {
            System.out.print(i + " ");
        }
    }
}
