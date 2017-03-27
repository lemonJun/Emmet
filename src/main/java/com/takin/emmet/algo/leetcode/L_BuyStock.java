package com.takin.emmet.algo.leetcode;

/**
 * 
 * Say you have an array for which the ith element is the price of a given stock on day i.
   Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
   (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple
   transactions at the same time (ie, you must sell the stock before you buy again).
 * @since 
 * @see
 */
public class L_BuyStock {
    /**
     * ֻ����һ�ʽ���
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int[] profit = new int[prices.length - 1];
        for (int i = 0; i < prices.length - 1; i++) {
            profit[i] = prices[i + 1] - prices[i];
        }

        int sum = 0, tmp = 0;
        for (int i = 0; i < profit.length; i++) {
            if (tmp < 0) {
                tmp = profit[i];
            } else {
                tmp += profit[i];
            }

            if (sum < tmp) {
                sum = tmp;
            }
        }
        return sum;
    }

    /**
     * ���ֻ��������ʽ���
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int[] profit = new int[prices.length - 1];
        for (int i = 0; i < prices.length - 1; i++) {
            profit[i] = prices[i + 1] - prices[i];
            System.out.print(profit[i] + " ");
        }

        int sum1 = 0, sum2 = 0;
        int tmp = 0;
        for (int i = 0; i < profit.length; i++) {
            if (tmp <= 0 || profit[i] <= 0) {//һ���µĽ��׵�Ŀ�ʼ
                tmp = profit[i];
                if (sum2 < sum1) {
                    sum2 = sum1;
                    sum1 = 0;
                }
            } else {
                tmp += profit[i];
            }
            if (sum1 < tmp) {
                sum1 = tmp;
            }
        }
        return sum1 + sum2;
    }

    public int maxSubArray(int[] A) {
        if (A == null || A.length < 1) {
            return 0;
        }

        int sum = Integer.MIN_VALUE, tmp = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            if (tmp < 0) {
                tmp = A[i];
            } else {
                tmp += A[i];
            }
            if (sum < tmp) {
                sum = tmp;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        int[] a = { -1, 2 };
        L_BuyStock ll = new L_BuyStock();
        int m = ll.maxSubArray(a);
        System.out.println();
        System.out.println(m);
    }
}
