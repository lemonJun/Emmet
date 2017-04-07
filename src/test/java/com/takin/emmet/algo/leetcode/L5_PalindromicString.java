package com.takin.emmet.algo.leetcode;

/**
 * ����Ļ����ִ�
 * Given a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, 
 * and there exists one unique longest palindromic substring.
 * @since 
 * @see
 * �����㷨����  ���ö�̬�滮����
 * 1 �� i < j ʱ��f(i,j) = 0;
 *   ��i=jʱ��f(i,j)=1;
 *   ��i<jʱ�����s[i]==s[j]  f(i,j)=f(i+1,j-1) +2;
 *   ��i<jʱ����� s[i]!=s[j] f(i,j) = max{f(i+1,j),f(i,j-1)}
 *   �ر�ĵ�i+1 = jʱ��f(i,j) = 2
 * 
 */
public class L5_PalindromicString {

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int len = s.length();
        int maxlen = 0;
        int index = 0;
        int mid = 0;
        int[][] matrix = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            matrix[i][i] = 1;
            int j;
            if (maxlen > mid - i) {
                j = Math.min(len, 2 * mid - i);
            }

            for (j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j) && matrix[i + 1][j - 1] == j - i - 1) {
                    matrix[i][j] = matrix[i + 1][j - 1] + 2;
                } else {
                    matrix[i][j] = Math.max(matrix[i + 1][j], matrix[i][j - 1]);
                }
            }
            if (maxlen < matrix[i][len - 1]) {
                maxlen = matrix[i][len - 1];
                index = i;
                mid = i + maxlen / 2;
            }
        }

        //        for (int i = 0; i < len; i++) {
        //            for (int j = 0; j < len; j++) {
        //                System.out.print(matrix[i][j] + " ");
        //            }
        //            System.out.println();
        //        }
        //        String ss = s.substring(index, index + maxlen);
        //        System.out.println(s.length());
        //        System.out.println(ss);
        //        System.out.println(ss.length());
        return s.substring(index, index + maxlen);
    }

    public static void main(String[] args) {
        Long start = System.nanoTime();
        L5_PalindromicString ll = new L5_PalindromicString();
        //        ll.longestPalindrome("a");
        String str = "321012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210012321001232100123210123210012321001232100123210123";
        //        String str = "abcdcbabcdcbwwwab";
        System.out.println(str);
        ll.longestPalindrome(str);

        System.out.println((System.nanoTime() - start) / 1000000L);
    }
}
