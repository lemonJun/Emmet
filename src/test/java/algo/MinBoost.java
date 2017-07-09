package algo;

public class MinBoost {

    public static void main(String[] args) {
        int[][] map = new int[][] { { 1, 2, 3 }, { 1, 1, 1 }, { 2, 1, 2 } };
        System.out.println(new MinBoost().getMin(map, 3, 3));
    }

    public int getMin(int[][] map, int row, int col) {
        int retval = 0;
        int[][] dp = new int[row][col];

        print(map, row, col);
        System.out.println("-------");

        for (int i = 0; i < row; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i][0] += map[j][0];
            }
        }
        print(dp, row, col);
        System.out.println("-------");
        for (int i = 0; i < col; i++) {
            for (int j = 0; j <= i; j++) {
                dp[0][i] += map[0][j];
            }
        }

        print(dp, row, col);
        System.out.println("-------");

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = min(dp[i - 1][j] + map[i][j], dp[i][j - 1] + map[i][j]);
            }
        }
        print(dp, row, col);
        retval = dp[row - 1][col - 1];
        return retval;
    }

    void print(int[][] dp, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int min(int a, int b) {
        if (a > b) {
            return b;
        } else {
            return a;
        }
    }

}
