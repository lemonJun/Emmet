package com.takin.algo.common;

public class HuiWen {
    public static void main(String[] args) {
        f2(123454321);
    }

    // 方法一
    public static void f1(int n) {
        if (n >= 10000 && n < 100000) {
            String s = String.valueOf(n);
            char[] c = s.toCharArray();
            if (c[0] == c[4] && c[1] == c[3]) {
                System.out.println(n + "是一个回文数。");
            } else {
                System.out.println(n + "不是一个回文数。");
            }
        } else {
            System.out.println(n + "不是一个5位数！！！");
        }
    }

    // 方法二
    public static void f2(int n) {
        boolean flag = true;
        String s = Long.toString(n);
        char[] c = s.toCharArray();
        int j = c.length;
        for (int i = 0; i < j / 2; i++) {
            if (c[i] != c[j - i - 1]) {
                flag = false;
            }
        }
        if (flag) {
            System.out.println(n + "是一个回文数。");
        } else {
            System.out.println(n + "不是一个回文数。");
        }
    }
}
