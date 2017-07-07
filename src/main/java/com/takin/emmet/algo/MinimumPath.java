package com.takin.emmet.algo;

/**
 * http://blog.csdn.net/p10010/article/details/50196211
 *
 * @author WangYazhou
 * @date  2017年7月7日 上午11:20:32
 * @see
 */
public class MinimumPath {

    public static void main(String[] args) {
        int[][] map = new int[][] { { 1, 2, 2, 1 }, { 2, 3, 4, 5 }, { 1, 4, 3, 2 }, { 1, 5, 8, 2 } };
        System.out.println(new MinimumPath().getMin(map, 4, 4));
    }

    public int getMin(int[][] map, int n, int m) {
        // write code here  
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i][0] += map[j][0];
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= i; j++) {
                dp[0][i] += map[0][j];
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = min(dp[i][j - 1] + map[i][j], dp[i - 1][j] + map[i][j]);
            }
        }
        return dp[n - 1][m - 1];
    }

    public int min(int a, int b) {
        if (a > b) {
            return b;
        } else {
            return a;
        }
    }
}
