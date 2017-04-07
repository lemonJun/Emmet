package com.takin.emmet.algo.leetcode;

import java.util.HashSet;
import java.util.Iterator;

public class L_SingleNum {
    public int singleNumber(int[] A) {
        if (A == null || A.length < 1) {
            return 0;
        }
        if (A.length == 1) {
            return A[0];
        }

        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < A.length; i++) {
            if (set.contains(A[i])) {
                set.remove(A[i]);
            } else {
                set.add(A[i]);
            }
        }

        Iterator ite = set.iterator();
        while (ite.hasNext()) {
            return (Integer) ite.next();
        }
        return 0;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode cur = head;
        ListNode fast = head.next;
        while (cur != null && fast != null) {
            if (cur == fast) {
                return true;
            }
            cur = cur.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                fast = fast.next;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = { 1, 1, 2 };
    }
}
