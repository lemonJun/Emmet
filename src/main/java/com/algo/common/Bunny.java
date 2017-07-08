package com.algo.common;

//有一只兔子，从出生后第3个月起每个月都生一只兔子，小兔子长到第四个月后每个月又生一只兔子，假如兔子都不死，问每个月的兔子总数为多少？
public class Bunny {
    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            int a = i;
            int sum = f(a);
            System.out.println("第" + a + "个月的兔子数为：" + sum);
        }
    }

    public static int f(int n) {
        int sum = 0;
        if (n == 1 || n == 2) {
            sum = 1;
        } else {
            sum = f(n - 1) + f(n - 2);
        }
        return sum;
    }
}
