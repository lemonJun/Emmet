package com.takin.emmet.algo.leetcode;

import java.util.HashSet;
import java.util.Set;

public class L_DuplicataList {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        Set<Integer> set = new HashSet<Integer>();
        set.add(head.val);
        ListNode parent = head;
        ListNode cur = head.next;
        while (cur != null) {
            int val = cur.val;
            if (set.contains(val)) {
                parent.next = cur.next;
                cur = cur.next;
            } else {
                set.add(val);
                parent = cur;
                cur = cur.next;
            }

        }

        return head;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
