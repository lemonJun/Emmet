package com.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FastSortByForkAndJoin {
    public static void main(String args[]) {
        File f = new File("D:/sample.txt");
        List arrayList = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String str = null;
            while ((str = reader.readLine()) != null) {
                arrayList.add(Integer.valueOf(str));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long longArray[] = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            longArray[i] = Long.parseLong(arrayList.get(i).toString());
        }

        ForkJoinPool pool = new ForkJoinPool();

        FastSort fastSort = new FastSort(longArray);

        long startTime = System.currentTimeMillis();
        pool.execute(fastSort);
        while (!fastSort.isDone()) {

        }

        long endTime = System.currentTimeMillis();

        System.out.println("排序所花时间:" + (endTime - startTime) + "ms");
        File f2 = new File("D:/fastSorted.txt");

        FileWriter writer2;
        try {
            writer2 = new FileWriter(f2, false);

            for (int i = 0; i < longArray.length; i++) {
                writer2.write(String.valueOf(longArray[i]));
                writer2.write("\r\n");
            }

            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

}

class FastSort extends RecursiveAction {

    final long[] array;
    final int lo;
    final int hi;
    private int THRESHOLD = 30;

    public FastSort(long[] array) {
        this.array = array;
        this.lo = 0;
        this.hi = array.length - 1;
    }

    public FastSort(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        if (hi - lo < THRESHOLD)
            sequentiallySort(array, lo, hi);
        else {
            int pivot = partition(array, lo, hi);
            FastSort left = new FastSort(array, lo, pivot - 1);
            FastSort right = new FastSort(array, pivot + 1, hi);

            invokeAll(left, right);
        }
    }

    private int partition(long[] array, int lo, int hi) {
        long x = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void sequentiallySort(long[] array, int lo, int hi) {
        Arrays.sort(array, lo, hi + 1);
    }
}
