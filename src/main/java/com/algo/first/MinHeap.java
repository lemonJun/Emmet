package com.algo.first;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    public static List<Integer> list = new ArrayList<Integer>();

    public static void add(int data) {
        list.add(data);
        shiftUp(list.size() - 1);
    }

    public static void shiftUp(int index) {
        int i_value = list.get(index);
        while (index > 0) {
            int p_index = (index - 1) / 2;
            int p_value = list.get(p_index);
            if (p_value > i_value) {
                list.set(index, p_value);
                index = p_index;
            } else {
                break;
            }

        }
        list.set(index, i_value);
    }

    public static Integer get() {
        if (list.isEmpty()) {
            return null;
        }
        int i = list.get(0);
        int last = list.remove(list.size() - 1);
        if (list.isEmpty()) {
            return i;
        }
        list.set(0, last);
        shiftDown(0);
        return i;
    }

    public static void shiftDown(int index) {
        int i_value = list.get(index);
        int l_index = 2 * index + 1;
        while (l_index < list.size()) {
            int min_index = l_index;
            int min_value = list.get(l_index);
            int r_index = l_index + 1;
            if (r_index < list.size()) {
                if (min_value > list.get(r_index)) {
                    min_index = r_index;
                    min_value = list.get(r_index);
                }
            }

            if (i_value > min_value) {
                list.set(index, min_value);
                index = min_index;
                l_index = 2 * index + 1;
            } else {
                break;
            }
        }

        list.set(index, i_value);

    }

    public static void main(String[] args) {
        MinHeap mh = new MinHeap();
        mh.add(3);
        mh.add(7);
        mh.add(10);
        mh.add(1);
        mh.add(5);
        mh.add(21);
        mh.add(6);

        for (int i : list) {
            System.out.print(i + " ");
        }
        System.out.println();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.print(get() + " ");
        }

    }
}
