package com.algo;

public class Permutation2 {
    public static void permutation1(String str, String result, int len) {
        /* 全排列 递归实现 
                 递归树：
          str:          a            b                c
                      ab ac         ba   bc         ca   cb
        result:      abc acb        bac    bca      cab   cba
         */
        //结果 开始传入""   空字符进入   len   是这个数的长度
        if (result.length() == len) { //表示遍历完了一个全排列结果
            System.out.println(result);
        } else {
            for (int i = 0; i < str.length(); i++) {
                if (result.indexOf(str.charAt(i)) < 0) { //返回指定字符在此字符串中第一次出现处的索引。
                    permutation1(str, result + str.charAt(i), len);
                }
            }
        }
    }

    public static void combination1() {
        /*全组合：
         * 思路是利用二进制的特性，每次加1即可遍历所有位的不同情况，很好理解代码同上
            */
        String arr[] = { "a", "b", "c" };
        int all = arr.length;
        int nbit = 1 << all;
        for (int i = 0; i < nbit; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < all; j++) {
                if ((i & (1 << j)) != 0) {
                    sb.append(arr[j]);
                }
            }
            System.out.println(sb);
        }
    }

    public static void main(String args[]) throws Exception {
        String s = "abc";
        String result = "";
        permutation1(s, result, s.length());
        //        combination1();
    }
}
