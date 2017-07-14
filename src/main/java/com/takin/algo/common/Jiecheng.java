package com.takin.algo.common;

public class Jiecheng {
    public static void main(String[] args) {
        int n = 5;
        long s = sum(n);
        System.out.println(n + "!= " + s);
    }

    public static long sum(int n) {
        long s = 1;
        if (n == 1 || n == 0) {
            s = 1;
        } else {
            s = n * sum(n - 1);
        }
        return s;
    }
}
