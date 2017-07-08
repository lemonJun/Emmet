package com.algo.first;

/**
 * 最大连续乘积子串
 * -2.5，4，0，3，0.5，8，-1    最大值为3*0.5*8 = 12
 * 个人感觉在处理时要注意的几个小地方：
 * 1 要求连续
 * 2 出现0的情况
 * 3 出现负数的情况
 * 连续比不连续的情况好在:求第i个数时   一定要把i乘进去  只需往前看一位 就可知道是接着乘 还是只保留自已
 * max[i] = max{data[i],max[i-1]*data[i],min[i-1]*data[i]}
 * min[i] = min{data[i],max[i-1]*data[i],min[i-1]*data[i]}
 * 
 * @since 
 * @see
 */
public class LargePlus {

    public double maxPlus(double[] data) {
        double ret = 0d;
        if (data == null || data.length < 1) {
            return ret;
        }
        double[] max = new double[data.length];
        double[] min = new double[data.length];
        max[0] = min[0] = data[0];
        for (int i = 1; i < data.length; i++) {
            max[i] = Math.max(Math.max(data[i], max[i - 1] * data[i]), min[i - 1] * data[i]);
            min[i] = Math.min(Math.min(data[i], max[i - 1] * data[i]), min[i - 1] * data[i]);
        }
        System.out.println("-------max=----------");
        for (double i : max) {
            System.out.print(i + " ");
        }
        System.out.println("\n------min----------");
        for (double j : min) {
            System.out.print(j + " ");
        }

        return ret;
    }

    public static void main(String[] args) {
        double[] data = { -2.5, 4, 0, 3, 0.5, 8, -1 };
        for (double i : data) {
            System.out.print(i + " ");
        }
        System.out.println();
        LargePlus lp = new LargePlus();
        lp.maxPlus(data);

    }

}
