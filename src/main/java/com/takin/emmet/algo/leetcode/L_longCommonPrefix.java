/*
 * BSM6.0
 *
 * Copyright 2012-2013 (C) SINODATA CO., LTD. All Rights Reserved.
 */
package com.takin.emmet.algo.leetcode;

/**
 * ����Ĺ���ǰ׺
 * Trie��   ��׺��
 * ����������� �����������ݽṹ
 * @since 
 * @see     
 */
public class L_longCommonPrefix {
    public String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0)
            return "";
        if (strs.length == 1)
            return strs[0];

        int minLength = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() <= minLength) {
                minLength = strs[i].length();
                index = i;
            }
        }
        String shortestStr = strs[index];
        int[] all = new int[minLength];

        for (int i = 0; i < strs.length; i++) {
            String eachStr = strs[i];
            for (int j = 0; j < minLength; j++) {
                if (eachStr.charAt(j) == shortestStr.charAt(j)) {
                    all[j]++;
                } else {
                    break;
                }
            }
        }

        String prefixStr = "";
        for (int j = 0; j < minLength; j++) {
            if (all[j] == strs.length) {
                prefixStr += shortestStr.charAt(j);
            } else {
                break;
            }
        }
        return prefixStr;
    }
}
