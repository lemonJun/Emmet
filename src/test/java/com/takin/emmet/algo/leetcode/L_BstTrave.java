package com.takin.emmet.algo.leetcode;

import java.util.ArrayList;

public class L_BstTrave {

    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root == null) {
            return list;
        }
        list.add(root.val);
        list.addAll(postorderTraversal(root.left));
        list.addAll(postorderTraversal(root.right));

        return list;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        L_BstTrave ll = new L_BstTrave();
        ArrayList<Integer> list = ll.postorderTraversal(node);
        for (int i : list) {
            System.out.print(i + " ");
        }
    }
}
