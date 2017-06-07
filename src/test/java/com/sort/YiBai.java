package com.sort;

public class YiBai {

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j < 101; j++) {
                if ((i + j) == 100) {
                    System.out.println(i + "-" + j);
                    break;
                }
            }
        }
    }
}
