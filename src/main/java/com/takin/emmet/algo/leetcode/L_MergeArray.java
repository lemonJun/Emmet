package com.takin.emmet.algo.leetcode;

public class L_MergeArray {

    public void merge(int A[], int m, int B[], int n) {

        if (m == 0 && n == 0) {
            return;
        }

        int[] array = new int[A.length];
        int i = 0, j = 0, cnt = 0;
        while (i < m && j < n) {
            if (A[i] <= B[j]) {
                array[cnt++] = A[i++];
            } else {
                array[cnt++] = B[j++];
            }
        }
        while (i < m) {
            array[cnt++] = A[i++];
        }

        while (j < n) {
            array[cnt++] = B[j++];
        }

        A = array;
        for (int k : A) {
            System.out.print(k + " ");
        }

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null;
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null && l2 != null) {
            head = l2;
            return head;
        }
        if (l1 != null && l2 == null) {
            head = l1;
            return head;
        }
        ListNode cur = null;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                if (head == null) {
                    head = l1;
                    cur = head;
                } else {
                    cur.next = l1;
                    cur = cur.next;
                }
                l1 = l1.next;
            } else {
                if (head == null) {
                    head = l2;
                    cur = head;
                } else {
                    cur.next = l2;
                    cur = cur.next;
                }
                l2 = l2.next;
            }
        }

        while (l1 != null) {
            cur.next = l1;
            cur = cur.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            cur.next = l2;
            cur = cur.next;
            l2 = l2.next;
        }

        return head;
    }

    public static void main(String[] args) {
        int[] a = new int[1];
        int[] b = { 1 };

        L_MergeArray ll = new L_MergeArray();
        ll.merge(a, 0, b, 1);
    }
}
