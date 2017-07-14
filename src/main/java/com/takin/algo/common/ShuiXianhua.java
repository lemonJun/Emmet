package com.takin.algo.common;

//打印出所有的"水仙花数"，所谓"水仙花数"是指一个三位数，其各位数字立方和等于该数本身。
//例如：153是一个"水仙花数"，因为153=1的三次方＋5的三次方＋3的三次方
public class ShuiXianhua {
    public static void main(String[] args) {
        s();
    }

    public static void s() {
        int count = 0;
        int a, b, c;
        for (int i = 100; i < 1000; i++) {
            a = i / 100;
            b = i % 100 / 10;
            c = i % 10;
            if (i == (a * a * a + b * b * b + c * c * c)) {
                count++;
                System.out.print(i + " ");
                if (count % 10 == 0) {
                    System.out.println();//显示换行处理
                }
            }
        }
        System.out.println("\n共有" + count + "个\"水仙花数\"。");
    }
}
