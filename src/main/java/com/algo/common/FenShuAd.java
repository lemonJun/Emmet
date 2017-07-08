package com.algo.common;

//有一分数序列：2/1，3/2，5/3，8/5，13/8，21/13...求出这个数列的前20项之和。
public class FenShuAd {
    public static void main(String[] args) {
        sum(20);
    }

    public static void sum(int n) {
        double x = 2.0;
        double y = 1.0;
        double t;
        double sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += (x / y);
            t = y;
            y = x;
            x = y + t;
        }
        System.out.println("前" + n + "项相和是： " + sum);
    }
}
