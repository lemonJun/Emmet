package com.takin.emmet.algo;

/**
 * http://blog.csdn.net/p10010/article/details/50196211
 *
 * @author WangYazhou
 * @date  2017年7月7日 上午11:30:44
 * @see
 */
public class GoUpstairs {

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(new GoUpstairs().countWays(i));
        }
    }

    public int countWays(int n) {
        // write code here  
        if (n <= 2)
            return n;
        int f = 1 % 1000000007;
        int s = 2 % 1000000007;
        int t = 0;
        for (int i = 3; i <= n; i++) {
            t = (f + s) % 1000000007;
            f = s;
            s = t;
        }
        return t;
    }
}
