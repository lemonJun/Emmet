package com.takin.algo.common;

public class DuplixADD {
    public static void main(String[] args) {
        sum(2, 5);
    }
    
    public static void sum(int a, int n) {
        int b = a;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += a;
            a = a * 10 + b;
        }
        System.out.println("和为：" + sum);
    }
}
