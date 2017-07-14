package com.takin.algo.common;

/**
 * 题目:给定一个存放整数的数组，请写一个算法，
 * 把偶数移动到该数组的右边，奇数放在该数组的左边，请考虑时间和空间的最优算法。
 * n&(n-1)==0 判断一个数字是否为2的指数
 * 
 */
public class EvenMove {
    //判断是否为偶数
    public static boolean isEven(int n) {
        return (n & 1) == 0;
    }

    //判断是否为偶数
    public static boolean isEven2(int n) {
        return (n % 2) == 0;
    }

    //定义算法
    public void order(int[] arr) {
        if (null == arr) {
            return;
        }

        int i = 0;
        int j = arr.length - 1;

        while (i < j) {

            //i是偶数，j是奇数
            if (isEven(arr[i]) && !isEven(arr[j])) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            } else if (!isEven(arr[i]) && isEven(arr[j])) {
                i++;
            } else if (isEven(arr[i]) && isEven(arr[j])) {
                j--;
            } else {
                i++;
            }
        }

    }

    public static void main(String[] args) {
        EvenMove arrayDemo = new EvenMove();
        int[] arr = { 4, 3, 5, 2, 10, 6, 7, 22, 23, 34, 45, 56 };
        arrayDemo.order(arr);
        for (int i : arr) {
            System.out.println(i);
        }

        System.out.println(isEven(1));
        System.out.println(isEven(12));
        System.out.println(isEven(13));
        System.out.println(isEven(14));
        System.out.println(isEven(15));
        System.out.println("--------------");
        System.out.println(1 & 1);
        System.out.println(12 & 1);
        System.out.println(13 & 1);
        System.out.println(14 & 1);
    }
}
