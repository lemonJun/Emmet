package com.takin.emmet.algo.leetcode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class L_WordBreak {
    /**
     * �������ݹ鷽��ʽȥ��  �����е�û�װ�  ���һ������޵ĵݹ�  
     * ��s="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     * dict="a" "aa" "aaa" "aaaa" "aaaaa" ��ʱ��  ����ʱ��
     * @param s
     * @param dict
     * @return
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() < 1) {
            return true;
        }
        if (dict == null || dict.size() < 1) {
            return false;
        }
        Iterator ite = dict.iterator();
        while (ite.hasNext()) {
            String dic = (String) ite.next();
            boolean b = false;
            if (s.startsWith(dic)) {
                b = wordBreak(s.substring(dic.length()), dict);
            }

            if (b) {
                return b;
            } else {
                continue;
            }

        }

        return false;
    }

    /**
     * �����ö�̬�滮�ķ�ʽȥ��  ������¥�ݵ�����
     * �Ե�i�� ������i-1������dict����һ���е�һ�����
     * @param s
     * @param dict
     * @return
     */
    public boolean wordBreak2(String s, Set<String> dict) {

        return false;
    }

    public static void main(String[] args) {
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        Set<String> dict = new HashSet<String>();
        dict.add("aaaa");
        dict.add("aaa");
        dict.add("aa");
        dict.add("a");
        dict.add("aaaaa");
        dict.add("aaaaaa");
        dict.add("aaaaaaa");
        dict.add("aaaaaaaa");
        System.out.println(s.substring(4));
        long start = System.nanoTime();
        L_WordBreak ll = new L_WordBreak();
        boolean b = ll.wordBreak(s, dict);
        System.out.println(b);
        System.out.println((System.nanoTime() - start) / 1000000);

    }
}
