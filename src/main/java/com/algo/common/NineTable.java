package com.algo.common;

public class NineTable {
    public static void main(String[] args) {
        table(9);
    }

    /**
     * 我用白话文解释"\t"的意思是：在同一个缓冲区内横向跳8个空格，JDK1.5上是这样的，至于更高版本是否变化，那我就不太清楚了！！
     * （有时候也有书籍称"\t"为制表符，对齐时使用的）
     * 
     * @param n
     */
    public static void table(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + j * i + "\t");
            }
            System.out.println();
        }
    }
}
