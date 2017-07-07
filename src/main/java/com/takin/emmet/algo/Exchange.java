package com.takin.emmet.algo;

public class Exchange {

    public static void main(String[] args) {
        int[] penny = new int[] { 1, 5, 10, 20, 50 };
        System.out.println(countWays(penny, 5, 100));
    }

    public static int countWays(int[] penny, int n, int aim) {
        // write code here  
        if (n == 0 || penny == null || aim < 0) {
            return 0;
        }
        int[][] pd = new int[n][aim + 1];
        for (int i = 0; i < n; i++) {
            pd[i][0] = 1;
        }
        for (int i = 1; penny[0] * i <= aim; i++) {
            pd[0][penny[0] * i] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= aim; j++) {
                if (j >= penny[i]) {
                    pd[i][j] = pd[i - 1][j] + pd[i][j - penny[i]];
                } else {
                    pd[i][j] = pd[i - 1][j];
                }
            }
        }

        for (int j = 0; j < aim + 1; j++) {
            for (int i = 0; i < n; i++) {
                System.out.print(pd[i][j] + " ");
            }
            System.out.println();
        }

        return pd[n - 1][aim];
    }
}
