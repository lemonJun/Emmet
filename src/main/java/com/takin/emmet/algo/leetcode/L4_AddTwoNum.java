package com.takin.emmet.algo.leetcode;

/**
 * 
 *You are given two linked lists representing two non-negative numbers. 
 *The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * @since 
 * @see
 */
public class L4_AddTwoNum {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode node = null;
        ListNode cur = null;
        int addnext = 0;
        while (l1 != null && l2 != null) {
            int val = (l1.val + l2.val + addnext) % 10;
            ListNode node1 = new ListNode(val);
            addnext = (l1.val + l2.val + addnext) / 10;
            if (node == null) {
                node = node1;
                cur = node;
            } else {
                cur.next = node1;
                cur = node1;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            if (addnext != 0) {
                ListNode nnode = new ListNode((l1.val + addnext) % 10);
                cur.next = nnode;
            } else {
                cur.next = l1;
            }
            addnext = (l1.val + addnext) / 10;
            cur = cur.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            if (addnext != 0) {
                ListNode nnode = new ListNode((l2.val + addnext) % 10);
                cur.next = nnode;
            } else {
                cur.next = l2;
            }
            addnext = (l2.val + addnext) / 10;
            cur = cur.next;
            l2 = l2.next;
        }

        if (addnext != 0) {
            ListNode nnode = new ListNode(addnext);
            cur.next = nnode;
        }

        return node;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(7);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        //        node2.next = node3;
        ListNode node21 = new ListNode(9);
        ListNode node22 = new ListNode(2);
        ListNode node23 = new ListNode(4);
        node21.next = node22;
        //        node22.next = node23;

        L4_AddTwoNum ll = new L4_AddTwoNum();
        ListNode node = ll.addTwoNumbers(node1, node21);

        while (node1 != null) {
            System.out.print(node1.val + "->");
            node1 = node1.next;
        }
        System.out.println();

        while (node21 != null) {
            System.out.print(node21.val + "->");
            node21 = node21.next;
        }

        System.out.println();

        while (node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
    }
}
