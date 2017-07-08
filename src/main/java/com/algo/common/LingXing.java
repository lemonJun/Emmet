package com.algo.common;

public class LingXing {
    public static void main(String[] args) {
        display(7);
    }

    public static void display(int h) {
        for (int i = 0; i < (h + 1) / 2; i++) {
            for (int j = 0; j < h / 2 - i; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k < (i + 1) * 2; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = 1; i <= h / 2; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < h - 2 * i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
