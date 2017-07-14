package algo1.common;

import java.util.Arrays;

/**
 * 两个int数组，都是从小到大的的排列，请合并为一个新的数组，也是从小到到大的排列，
 * 请写出性能最优的算法。<br>
 * 
 * 要是要求去重呢
 * 已经排好序了 ，两者递归也是好的  这个求法不对
 * 
 *
 */
public class IntMerger {
    public static void main(String[] args) {
        int[] a = { 1, 3, 5, 7, 9 };
        int[] b = { 2, 4, 6, 7, 8, 10 };

        int[] c = new int[a.length + b.length];

        //将a,b的数据copy到c中
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        Arrays.sort(c);

        for (int i : c) {
            System.out.print(i);
        }

    }
}
