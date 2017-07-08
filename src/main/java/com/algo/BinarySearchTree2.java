package com.algo;

public class BinarySearchTree2 {

    private class TreeNode {

        private int key;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private TreeNode parent;

        public TreeNode(int key, TreeNode leftChild, TreeNode rightChild, TreeNode parent) {
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
        }

        public int getKey() {
            return key;
        }
    }

    private TreeNode root = null;

    public void insert(int key) {
        TreeNode parentnode = null;
        TreeNode pnode = root;
        TreeNode newnode = new TreeNode(key, null, null, null);
        if (root == null) {
            root = newnode;
            return;
        }

        while (pnode != null) {
            parentnode = pnode;
            if (key < pnode.key) {
                pnode = pnode.leftChild;
            } else if (key > pnode.key) {
                pnode = pnode.rightChild;
            } else {
                return;
            }
        }

        if (key < parentnode.key) {
            parentnode.leftChild = newnode;
        } else {
            parentnode.rightChild = newnode;
        }
        newnode.parent = parentnode;
    }

    public void middleTrave(TreeNode node) {
        TreeNode current = node;
        if (current == null) {
            return;
        }

        middleTrave(current.leftChild);
        System.out.print(current.key + " ");
        middleTrave(current.rightChild);
    }

    public void preTrave(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.key + " ");
        preTrave(node.leftChild);
        preTrave(node.rightChild);
    }

    public void succTrave(TreeNode node) {
        if (node == null) {
            return;
        }
        succTrave(node.leftChild);
        succTrave(node.rightChild);
        System.out.print(node.key + " ");
    }

    public static void main(String[] args) {
        BinarySearchTree2 bst = new BinarySearchTree2();
        int[] keys = new int[] { 15, 6, 18, 3, 7, 13, 20, 2, 9, 4 };
        for (int key : keys) {
            bst.insert(key);
        }

        bst.middleTrave(bst.root);
        System.out.println();
        bst.preTrave(bst.root);
        System.out.println();
        bst.succTrave(bst.root);
    }

}
