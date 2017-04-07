package com.takin.emmet.algo.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ������������Ĳ��  �������
 * �����������������queueʵ�� 
 *
 * @since 
 * @see
 */
public class L_BstLevelTravel {
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        if (root == null) {
            return list;
        }
        TreeNode nil = new TreeNode(Integer.MIN_VALUE);
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(nil);
        queue.offer(root);
        ArrayList<Integer> data = null;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == nil && queue.peek() != null) {
                data = new ArrayList<Integer>();
                list.add(data);
                queue.offer(nil);
            }
            if (node != null && node != nil) {
                data.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        if (list.size() > 0) {
            ArrayList<Integer> tmp = list.get(list.size() - 1);
            if (tmp == null || tmp.size() < 1) {
                list.remove(tmp);
            }
        }

        return list;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return true;
        }
        if ((p == null && q != null) || (q != null && p == null)) {
            return false;
        }

        return false;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        System.out.println((0 & 1) == 0);
    }
}
