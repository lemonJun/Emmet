/*
 * BSM6.0
 *
 * Copyright 2012-2013 (C) SINODATA CO., LTD. All Rights Reserved.
 */
package com.algo.first;

/**
 * 每个节点有五个属性：key left right parent color
 * 解决的是二叉查找树不平衡的问题
 * 它有五个属性：每个节点要么红要么黑  顶节点为黑色，叶子节点为黑色，红节点下的子节点为黑色，每个节点到其后代节点包含相同数目的黑节点
 * 不明白为什么红黑树需要这个nil节点 
 * 红黑树只是二叉查找树的变种  ，它只是利用颜色确保了二叉查找到的平衡性质
 * 左旋 右旋 其实只是在改变节点的各种关系   时间复杂度为O(1)
 *
 * @since 
 * @see     
 */
public class RBTree {

    private static final String _BLACK = "black";
    private static final String _RED = "red";

    static class FirstRBNode {
        Integer key;
        FirstRBNode left;
        FirstRBNode right;
        FirstRBNode parent;
        String color;

        public FirstRBNode(int data) {
            key = data;
            left = null;
            right = null;
            parent = null;
            color = _RED;
        }

        public FirstRBNode() {

        }

        public FirstRBNode(int key, FirstRBNode left, FirstRBNode right, FirstRBNode parent, String color) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }
    }

    //空节点  
    private final FirstRBNode nil = new FirstRBNode(-1);
    public FirstRBNode root = null;

    public RBTree() {

    }

    public FirstRBNode search(int data) {
        FirstRBNode node = root;
        while (node != null) {
            if (data == node.key) {
                return node;
            }
            if (data > node.key) {
                node = node.right;
            }
            if (data < node.key) {
                node = node.left;
            }
        }
        return null;
    }

    //
    public FirstRBNode processor(FirstRBNode node) {
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            return max(node.left);
        } else {
            FirstRBNode parent = null;
            while (node != null) {
                parent = node.parent;
                if (parent != null && parent.right == node) {
                    node = node.parent;
                } else {
                    break;
                }
            }
            return parent;
        }
    }

    //
    public FirstRBNode successor(FirstRBNode node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return min(node.right);
        } else {
            FirstRBNode parent = null;
            while (node != null) {
                parent = node.parent;
                if (parent != null && parent.left == node) {
                    node = node.parent;
                }
            }
            return parent;
        }

    }

    /**
     * 左旋
     * 1 获取到y
     * 2 把y的左子树变成x的右子树
     * 3 把x的parent置为 y的parent
     * 4 把x置为y的左子节
     * @param node
     */
    public void leftRotate(FirstRBNode x) {
        FirstRBNode y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    /**
     * 右旋
     * 1 获取到y 
     * 2 y的左节点设为x的右节点
     * 3 x的parent置为y的parent
     * 4 把x置为y的右节点
     * @param x
     */
    public void rightRotate(FirstRBNode x) {
        FirstRBNode y = x.left;
        y.left = x.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.right = x;
        x.parent = y;

    }

    //
    public FirstRBNode max(FirstRBNode node) {
        if (node == null) {
            return null;
        }
        FirstRBNode cur = node;
        while (node != null) {
            cur = node;
            node = node.right;
        }

        return cur;
    }

    //
    public FirstRBNode min(FirstRBNode node) {
        if (node == null) {
            return null;
        }

        FirstRBNode cur = node;
        while (node != null) {
            cur = node;
            node = node.left;
        }
        return cur;
    }

    /**
     * 
     * @param data
     */
    public void insert(int data) {
        FirstRBNode node = new FirstRBNode(data);
        if (root == null) {
            root = node;
            root.color = _BLACK;
            return;
        }

        FirstRBNode nNode = root;
        FirstRBNode parent = root;
        while (nNode != null) {
            parent = nNode;
            if (data >= nNode.key) {
                nNode = nNode.right;
            } else {
                nNode = nNode.left;
            }
        }

        if (data >= parent.key) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;

        insert_fix(node);
    }

    public void insert_fix(FirstRBNode node) {

    }
}
