package com.algo.first;

public class Kmp {

    public boolean kmpMatch(String str1, String str2) {

        return false;
    }

    /**
     * 对于覆盖函数各值的意义
     * next[0] = -1  ，字符串的第一个字符的模式值为-1
     * next[j] = -1, T[j]==T[0]且(j-k与开头k-1个字符不同/T[j]==T[k])
     * next[j]= k,j-k个数与开头k-1个数相等，且T[k]!=T[j]  
     * next[j] = 0
     * @param str2  abac
     * @return
     */
    public int[] next(String str2) {
        int[] data = new int[str2.length()];
        data[0] = -1;
        int k = 0;
        for (int i = 1; i < str2.length(); i++) {
            while (k > 0 && str2.charAt(k) != str2.charAt(i)) {
                k = data[k - 1];
            }
            if (str2.charAt(k) == str2.charAt(i)) {
                k = k + 1;
            }
        }

        for (int i : data) {
            System.out.print(i + " ");
        }

        return data;
    }

    /**
     * 当s[i]!=p[j]时，其前面的值肯定是匹配的就有关系s[0....i-1] == p[0...j-1]
     * 而对于next[j]的计算给的公式是s[0...k-1] = p[j-k...j-1] 
     * @param str1
     * @param str2
     * @return
     */
    public boolean match(String str1, String str2) {
        int[] next = new int[str2.length()];
        int i = 0, j = -1;
        while (i < str1.length() && j < str2.length()) {
            if (str1.charAt(i) == str2.charAt(j) || j == -1) {
                i++;
                j++;
            } else {
                j = next[j];//代表的是下一个可用来匹配的位置
            }
        }
        return j >= str2.length();
    }

    public static void main(String[] args) {
        String str1 = "abacababc";
        String str2 = "abaabcaba";

        Kmp kmp = new Kmp();
        kmp.next(str2);
        //        kmp.kmpMatch(str1, str2);
    }
}
