package com.takin.emmet.algo.leetcode;

public class L_MatrixSearch {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length < 1) {
            return false;
        }

        int n = matrix.length;
        int m = matrix[0].length;
        int i = 0, j = m - 1;
        while (i < n && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (target > matrix[i][j]) {
                i++;
            } else {
                j--;
            }
        }

        return false;
    }

    public boolean bisearch(int[] data, int target) {
        if (data == null || data.length < 1) {
            return false;
        }
        int index = Integer.MIN_VALUE;
        int begin = 0;
        int end = data.length - 1;
        while (index != 0 && index != end) {
            index = (begin + end) / 2;
            int value = data[index];
            if (value == target) {
                return true;
            } else if (target > value) {
                begin = index;
            } else {
                end = index;
            }
        }

        return false;
    }

    public void setZeroes(int[][] matrix) {

    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 1 } };
        // System.out.println(matrix.length);
        //System.out.println(matrix[0].length);

        L_MatrixSearch ll = new L_MatrixSearch();
        int[] a = { 1, 3, 5, 7, 9, 12 };

        //System.out.println(ll.bisearch(a, 7));

        System.out.println();

        System.out.println(ll.searchMatrix(matrix, 2));

    }
}
