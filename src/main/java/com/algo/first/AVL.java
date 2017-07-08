package com.algo.first;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 平衡二叉查找树
 * 最重要的是  每插入一个节点需要靠旋转操作来维持二叉查找树的平衡性质
 * 
 * @since 
 * @see
 */
public class AVL {

    Queue<String> q = new LinkedList<String>();

    public static AVNode root = null;

    static class AVNode {
        Integer key;
        AVNode parent;
        AVNode left;
        AVNode right;

        public AVNode(int data) {
            key = data;
            parent = null;
            left = null;
            right = null;
        }
    }

    public static void insert(int data) {
        AVNode node = new AVNode(data);
        if (root == null) {
            root = node;
        }

        AVNode cur = root;
        while (cur != null) {
            if (data >= cur.key) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }

        if (data >= cur.key) {
            cur.right = node;
        } else {
            cur.left = node;
        }
        node.parent = cur;

        //to-do
    }

    /**
     * 以node节点做右旋转操作
     *      100<-node.parent           80<-node.parent 
     *      /                                      /  \ 
     *     80             ———>         60   100 
     *    /  \                                  / 
     *   60  85                            85 
     * 
     * @param node
     */
    public static void rRotate(AVNode node) {

    }

}
