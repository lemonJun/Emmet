package com.takin.emmet.algo.leetcode;

/**
 * n�׵�¥��   �ڴ�ֻ������  1 ��  2��
 * �ʹ��ж������߷�:
 * 1 1 
 * 2 2+(1+1)=2
 * 3 (2+1)+(1+2)+(1+1+1) =3   (f(1))+(f(2)) = 3
 * 4 
 * 
 * @since 
 * @see
 */
public class L_ClimbStairs {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        int[] step = new int[n + 2];
        step[1] = 1;
        step[2] = 2;
        if (n < 3) {
            return step[n];
        }

        for (int i = 3; i <= n; i++) {
            step[i] = step[i - 1] + step[i - 2];
        }

        return step[n];
    }

    public static void main(String[] args) {
        L_ClimbStairs ll = new L_ClimbStairs();
        int data = ll.climbStairs(4);
        System.out.println(data);
    }
}
