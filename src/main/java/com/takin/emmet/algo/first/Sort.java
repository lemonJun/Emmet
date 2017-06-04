/*
 * BSM6.0
 *
 * Copyright 2012-2013 (C) SINODATA CO., LTD. All Rights Reserved.
 */
package com.takin.emmet.algo.first;

import java.util.Random;

/**
 * 插入排序   
 * 快速排序    
 * 堆   
 * 平衡二叉树   
 * Trie树   
 * 二分查找   
 * 
 * @since 
 * @see     
 */
public class Sort {

    /**
     * 这是选择的最后一个元素做为基准点   
     * 可以有两种改进：选择第一个元素   随机选择一个元素
     * 此为递归版本实现
     * @param data
     * @param left
     * @param right
     */
    public void insertsort(int[] data, int left, int right) {
        if (left < right) {
            int q = partion(data, left, right);
            insertsort(data, left, q - 1);
            insertsort(data, q + 1, right);
        }

        for (int i : data) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public int partion(int[] data, int p, int r) {
        int x = data[r];
        int i = p - 1;
        int tmp;
        for (int j = p; j < r; j++) {
            if (data[j] <= x) {
                i = i + 1;
                tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
            }
        }
        tmp = data[i + 1];
        data[i + 1] = data[r];
        data[r] = tmp;
        return i + 1;
    }

    /**
     * 问题：一个最多含有n个不重复的正整数的文件，其中每个数都小于n，民为10^7
     * 要求对正整数排序   内存空间小于1M
     * 分析：10^10对应内存为1G,帮10^7对应内存为1M
     * 
     * 
     * 
     * @param args
     */
    public void bitmap() {

    }

    public void writefile() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            System.out.println(random.nextInt(10));
        }

    }

    public static void main(String[] args) {
        //        ArrayList<Character> cl = new ArrayList<Character>(26);
        //
        int[] data = { 6, 3, 2, 5, 16, 23, 12, 8, 10, 9 };
        Sort sort = new Sort();
        sort.insertsort(data, 0, data.length - 1);

        //        sort.writefile();
    }
}
