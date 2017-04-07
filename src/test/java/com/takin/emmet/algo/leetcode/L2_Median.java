package com.takin.emmet.algo.leetcode;

/**
 * 
 * There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.
 * The overall run time complexity should be O(log (m+n)).
 * 
 * ��λ�����ź����   ���������м�λ�õ������������Ϊ��������ѡ��n/2+1����������Ϊż������ȡn/2 �� n/2+1��ƽ��ֵ
 * �����һ������ ��ô����λ���أ�����  ȡ�м�ֵ
 * 
 * 2 4 6 8 
 * 3 5 7 
 * 
 * 2 3 4 5 6 7 8 
 * 
 * 
 * @since 
 * @see
 */
public class L2_Median {
    public double findMedianSortedArrays(int A[], int B[]) {
        if (A == null || A.length == 0 && (B == null || B.length == 0)) {
            return 0d;
        }

        if ((A == null || A.length == 0) && B.length > 0) {
            if (B.length % 2 == 0) {
                return ((double) (B[(B.length - 1) / 2]) + (double) B[(B.length - 1) / 2 + 1]) / 2;
            } else {
                return (double) B[B.length / 2];
            }

        }

        if (A.length > 0 && (B == null || B.length == 0)) {
            if (A.length % 2 == 0) {
                return ((double) (A[(A.length - 1) / 2]) + (double) A[(A.length - 1) / 2 + 1]) / 2;
            } else {
                return (double) A[A.length / 2];
            }
        }

        if (A.length == 1 && B.length == 1) {
            return (double) (A[0] + B[0]) / 2;
        }

        if (A.length == 1) {
            if (A[0] < B[B.length / 2]) {
                return Math.max(A[0], B[B.length / 2 - 1]);
            } else {
                return A[0];
            }
        }

        if (B.length == 1) {
            if (B[0] < A[A.length / 2]) {
                return Math.max(B[0], A[A.length / 2 - 1]);
            } else {
                return B[0];
            }
        }

        int abegin = 0;
        int aend = A.length - 1;

        int bbegin = 0;
        int bend = B.length - 1;

        while ((aend - abegin != 1) && (bend - bbegin != 1)) {
            int amid = (abegin + aend) / 2;
            int bmid = (bbegin + bend) / 2;

            if (A[amid] < B[bmid]) {
                abegin = amid;
                bend = bmid;
            } else {
                aend = amid;
                bbegin = bmid;
            }
        }

        int[] arry = new int[4];
        int i = 0, j = 0, k = 0;
        while (i < 2 && j < 2) {
            if (A[i] <= B[j]) {
                arry[k++] = A[abegin++];
                i++;
            } else {
                arry[k++] = B[bbegin++];
                j++;
            }
        }

        while (i < 2) {
            arry[k++] = A[abegin++];
            i++;
        }
        while (j < 2) {
            arry[k++] = B[bbegin++];
            j++;
        }
        return ((double) arry[1] + (double) arry[2]) / 2;
    }

    public static void main(String[] args) {
        L2_Median ll = new L2_Median();
        int[] a = { 2, 3 };
        int[] b = {};

        System.out.println(ll.findMedianSortedArrays(a, b));
    }
}
