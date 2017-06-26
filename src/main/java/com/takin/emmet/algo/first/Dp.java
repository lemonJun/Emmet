//package com.takin.emmet.algo.first;
//
///**
// * 这是一种思想，而并非一种固定的技术 线性规划、区域规划、树型规划、背包动规 划分阶段 找出状态 表述状态转移方程(递推关系)
// * 
// *
// * @since
// * @see
// */
//public class Dp {
//
//    /**
//     * 求两个字符串的最长公共子序列 递归关系式： 0 if xi==0 or yi==0 lcs[x-1][y-1] if xn==yn
//     * max{lcs[x-1]y,lcs[x][y01] if xn!= yn
//     * 
//     * 由此可以写出一个递归算法，但是时间复杂度为指数级的，指数是多少？怎么算的
//     * 
//     * @param str1
//     * @param str2
//     * @return
//     */
//    public static String lcs1(String str1, String str2) {
//        int len1 = str1.length();
//        int len2 = str2.length();
//        if (len1 == 0 || len2 == 0) {
//            return "";
//        }
//
//        if (str1.charAt(len1 - 1) == str2.charAt(len2 - 1)) {
//            if (len1 > 1 && len2 > 1) {
//                return lcs1(str1.substring(0, len1 - 2), str2.substring(0, len2 - 2)) + str1.charAt(len1 - 1);
//            } else {
//                return "";
//            }
//        } else {
//            if (len1 > 1 && len2 > 1) {
//                String tmp1 = lcs1(str1.substring(0, len1 - 1), str2.substring(0, len2 - 2));
//                String tmp2 = lcs1(str1.substring(0, len1 - 2), str2.substring(0, len2 - 1));
//                return tmp1.length() > tmp2.length() ? tmp1 : tmp2;
//            } else {
//                return "";
//            }
//        }
//    }
//
//    public static String lastcommonsequence(String str1, String str2) {
//        int lenx = str1.length();
//        int leny = str2.length();
//        int[][] c = new int[lenx + 1][leny + 1];
//        for (int i = 0; i <= lenx; i++) {
//            for (int j = 0; j <= leny; j++) {
//                c[i][j] = 0;
//            }
//        }
//
//        // 生成线咱数据值
//        for (int i = lenx - 1; i > 0; i--) {
//            for (int j = leny - 1; j > 0; j--) {
//                if (str1.charAt(i) == str2.charAt(j)) {
//                    c[i][j] = c[i + 1][j + 1] + 1;
//                } else {
//                    c[i][j] = Math.max(c[i + 1][j], c[i][j + 1]);
//                }
//            }
//        }
//
//        // 组装字符串
//        StringBuilder sb = new StringBuilder("");
//        int i = 0, j = 0;
//        while (i < lenx && j < leny) {
//            if (str1.charAt(i) == str2.charAt(j)) {
//                sb.append(str1.charAt(i));
//                i++;
//                j++;
//            } else if (c[i + 1][j] >= c[i][j + 1]) {
//                i++;
//            } else {
//                j++;
//            }
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * 问题：N个人，要求从中移出N-k个人，使得剩下的k个人满足合唱队型 合唱队型：hi<h2<h3...<hi>hi+1>hi+2...>hk 分析：
//     * 
//     * @param data
//     * @return
//     */
//    public static int singqueue(int[] data) {
//
//        return 0;
//    }
//
//    /**
//     * 导弹拦截问题： 一个导弹系统，第一发炮弹能达到任意高度，以后每一发炮弹的高度都不能高于前一发 输入为依次飞来的导弹的高度如：389 207 155
//     * 300 299 170 158 65 输出一套系统最多能拦截的导弹数，及要拦截所有导弹需要的系统数，6 2 分析：
//     * 
//     * @param data
//     * @return
//     */
//    public static int[] missileproblem(int[] data) {
//
//        return null;
//    }
//
//    /**
//     * 地雷问题: N个地窑，每个地窑中埋有一定数量的地雷及与其它地窑的联系路径，可以从任一处开始挖地雷，然后沿着指定的路径往下挖，
//     * 挖雷的过程中允许重复经过地窑，当无连接时，挖雷工作结束。求一个挖雷最多的算法。
//     * 
//     * @return
//     */
//    public static int mine() {
//
//        return 0;
//    }
//
//    /**
//     * 01背包：M件物品取出若干件放在空间为W的背包里，每件物品的体积为W1,W2,W3....Wn，与之相应的价值为P1,P2...Pn
//     * 求获取最大价值的方案 递归方程：f[i,j]=max{f[i-1,j-Wi]+Pi (j>=Wi), f[i-1,j]}
//     * 第i件物品不放，最大价值就是前i-1个特口的价值 第i件物品要放，最大价值就是在W-WIi的空间里， 放i-wi个物品的价值+Pi
//     */
//    public static void knapsack() {
//        int[] w = { 1, 2, 3, 4, 5, 6 };
//        int[] p = { 3, 4, 5, 6, 8, 1 };
//
//    }
//
//    /**
//     * 最长非降子序列
//     * 
//     * @param str
//     */
//    public static void lis(int[] a) {
//        int[] dp = new int[a.length];
//        int[] pre = new int[a.length];
//
//        for (int i = 0; i < a.length; i++) {
//            dp[i] = 1;
//            pre[i] = -1;
//            for (int j = 0; j < i; j++) {
//                if (a[j] < a[i]) {
//                    if (dp[j] + 1 > dp[i]) {
//                        dp[i] = dp[j] + 1;
//                        pre[i] = j;
//                    }
//                } else {
//                    dp[i] = dp[j];
//                    pre[i] = j;
//                }
//            }
//        }
//
//        for (int i : dp) {
//            System.out.print(i + " ");
//        }
//        System.out.println();
//        for (int i : pre) {
//            System.out.print(i + " ");
//        }
//
//        int max = dp[0];
//        for (int i : dp) {
//            if (max < i) {
//                max = i;
//            }
//        }
//        System.out.println();
//        System.out.println("max==" + max);
//
//        while (max != -1) {
//            System.out.println(a[max] + " ");
//            max = pre[max];
//        }
//    }
//
//    public static void main(String[] args) {
//        // try {
//        // System.out.println(lastcommonsequence("bevxynocpuagnhnndwpp",
//        // "kzvyxxkpbnbxghnlochz"));
//        //
//        // // System.out.println(lcs1("bevxynocpuagnhnndwpp",
//        // "kzvyxxkpbnbxghnlochz"));
//        // } catch (Exception e) {
//        // e.printStackTrace();
//        // }
//
//        int[] a = { 2, 4, 5, 1, 3, 7, 10, 6, 9 };
//
//        int[] b = { 1, 2, 3, 8, 15, 13 };
//        lis(a);
//    }
//}
