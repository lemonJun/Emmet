package com.takin.emmet.algo.leetcode;

/**
 * ������ظ����Ӵ�
 * Given a string, find the length of the longest substring without repeating characters.
   For example, the longest substring without repeating letters for "abcabcbb" is "abc",
   which the length is 3. For "bbbbb" the longest substring is "b", with the length of 1.
 *
 *
 * �ö�̬�滮������
 * ״̬�����ڵ�k���ַ�Sk��ʹ��S1,S2..Sk�������ظ����ַ�
 * ״̬ת�Ʒ��̣����k��ǰ�������ظ��Ӵ����ַ�û���ظ�������kΪ��β����һ����������ظ��Ӵ�
 *               ������ظ�������ĳ���Զ̵��Ӵ������µ����� �� ������Ϊһ���Ӵ�
 */
public class L3_RepeatLetter {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        int maxlen = 0;
        int[] dp = new int[s.length()];
        dp[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i] = Math.min(i - j, dp[i - 1] + 1);
                    break;
                }
            }

            if (j == -1) {
                dp[i] = dp[i - 1] + 1;
            }

            if (dp[i] > maxlen) {
                maxlen = dp[i];
            }
        }
        return maxlen;
    }

    public static void main(String[] args) {
        L3_RepeatLetter ll = new L3_RepeatLetter();
        System.out.println(ll.lengthOfLongestSubstring("ab"));

    }
}
