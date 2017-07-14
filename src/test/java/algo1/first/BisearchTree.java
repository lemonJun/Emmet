package algo1.first;

import java.util.ArrayList;
import java.util.List;

/**
 *  每个节点有四个属性: key(value) left right parent  比RB树少一个  要parent是用来求前驱和后继的
 *  不同的树可以代表相同的数据集合
 *  不同的输入顺序  会生成不同形状的树 
 *  理解前驱和后继的定义    就好写代码了   且两个都是对值大小的定义   不是对树结构的定义
 *  前驱：
 *  后继：在大于x的值中 最小的那个 
 *  
 *  给定输入数组  可以生成二叉树 ，默认按先序遍历的方式生成的  
 *  树的先 中 后序遍历  说明先访问到的节点在先 中 后输出
 *  树遍历的三种策略： 递归(递归也用到了栈)  迭代(栈+iterator)   前两个需要O(n)的空间
 *  morris遍历：只需要O(1)的空间   具体方法实现与策略描述略有些不一样 
 *  
 *  
 *  
 *  
 * @since 
 * @see
 */
public class BisearchTree {

    private static List<FirstNode> items = new ArrayList<FirstNode>();
    private static FirstNode tmp = null;

    private static FirstNode root = null;

    static class FirstNode {
        private Integer key;
        private FirstNode left;
        private FirstNode right;
        private FirstNode parent;

        public FirstNode(int value) {
            key = value;
            parent = null;
            left = null;
            right = null;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public FirstNode getLeft() {
            return left;
        }

        public void setLeft(FirstNode left) {
            this.left = left;
        }

        public FirstNode getRight() {
            return right;
        }

        public void setRight(FirstNode right) {
            this.right = right;
        }

        public FirstNode getParent() {
            return parent;
        }

        public void setParent(FirstNode parent) {
            this.parent = parent;
        }

    }

    public static void add(int data) {
        FirstNode nNode = new FirstNode(data);
        if (data == 6) {
            tmp = nNode;
        }
        items.add(nNode);
        if (root == null) {
            root = nNode;
            return;
        }
        FirstNode pNode = root;
        FirstNode parent = root;
        while (pNode != null) {
            parent = pNode;
            if (data >= pNode.getKey()) {
                pNode = pNode.getRight();
            } else {
                pNode = pNode.getLeft();
            }
        }

        if (data >= parent.getKey()) {
            parent.setRight(nNode);
        } else {
            parent.setLeft(nNode);
        }
        nNode.setParent(parent);

    }

    public static FirstNode search(int data) {
        FirstNode node = root;
        while (node != null) {
            if (data == node.getKey()) {
                return node;
            }
            if (data > node.getKey()) {
                node = node.getRight();
            }
            if (data < node.getKey()) {
                node = node.getLeft();
            }
        }
        return null;
    }

    //所有的递归实现  当层次较深时 效率都会很低
    //并且为保存遍历结果  需要O(n)的空间
    /**
     * 中序遍历的递归实现 
     * 
     */
    public static void orderWalk(FirstNode node) {
        if (node == null) {
            return;
        }
        orderWalk(node.getLeft());
        System.out.print(node.getKey() + " ");
        orderWalk(node.getRight());
    }

    /**
     * 前序遍历的递归实现
     * @param node
     */
    public static void frontWalk(FirstNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getKey() + " ");
        frontWalk(node.getLeft());
        frontWalk(node.getRight());
    }

    /**
     * 后序遍历的递归实现
     * @param node
     */
    public static void backWalk(FirstNode node) {
        if (node == null) {
            return;
        }
        backWalk(node.getLeft());
        backWalk(node.getRight());
        System.out.print(node.getKey() + " ");
    }

    //
    //morris遍历
    //可以做到O(1)的空间
    /**
     * 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
       2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
       a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
       b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。
     */

    public static void morrisOrderWalk(FirstNode node) {
        FirstNode cur = node;
        while (cur != null) {
            if (cur.getLeft() == null) {
                System.out.print(cur.getKey() + " ");
                cur = cur.getRight();
            } else {
                //并不是简单的找到前驱   而是要求一定要在在子树中寻找
                //FirstNode proc = processor(cur);
                FirstNode proc = cur.getLeft();
                while (proc.getRight() != null && proc.getRight() != cur) {
                    proc = proc.getRight();
                }

                if (proc.getRight() == null) {
                    proc.setRight(cur);
                    cur = cur.getLeft();
                } else {
                    proc.setRight(null);
                    System.out.print(cur.getKey() + " ");
                    cur = cur.getRight();
                }
            }
        }
    }

    /**
     * 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
        2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
        a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。输出当前节点（在这里输出，这是与中序遍历唯一一点不同）。当前节点更新为当前节点的左孩子。
        b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空。当前节点更新为当前节点的右孩子。
     * @param node
     */
    public static void morrisProcWalk(FirstNode node) {
        FirstNode cur = node;
        while (cur != null) {
            if (cur.getLeft() == null) {
                System.out.print(cur.getKey() + " ");
                cur = cur.getRight();
            } else {
                FirstNode proc = cur.getLeft();
                while (proc.getRight() != null && proc.getRight() != cur) {
                    proc = proc.getRight();
                }
                if (proc.getRight() == null) {
                    System.out.print(cur.getKey() + " ");
                    proc.setRight(cur);
                    cur = cur.getLeft();
                } else {
                    proc.setRight(null);
                    cur = cur.getRight();
                }
            }
        }
    }

    /**
     * 1. 如果当前节点的左孩子为空，则将其右孩子作为当前节点。
       2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
         a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
         b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空。倒序输出从当前节点的左孩子到该前驱节点这条路径上的所有节点。
                      当前节点更新为当前节点的右孩子。
     * @param node
     */
    public static void morrisBackWalk(FirstNode node) {
        FirstNode cur = node;
        while (cur != null) {
            if (cur.getLeft() == null) {
                cur = cur.getRight();
            } else {
                FirstNode proc = cur.getLeft();
                while (proc.getRight() != null && proc.getRight() != cur) {
                    proc = proc.getRight();
                }
                if (proc.getRight() == null) {
                    proc.setRight(cur);
                    cur = cur.getLeft();
                } else {
                    //倒序输出从当前节点的左孩子到该前驱节点这条路径上的所有节点  ??
                    FirstNode dump = proc;
                    while (dump != cur.getLeft()) {
                        System.out.print(dump.getKey() + " ");
                        dump = dump.getParent();
                    }
                    System.out.print(cur.getLeft().getKey() + " ");
                    proc.setRight(null);
                    cur = cur.getRight();
                }
            }
        }
    }

    /**
     * 查找一个节点的前驱节点
     * @param node
     * @return
     */
    public static FirstNode processor(FirstNode node) {
        if (node == null) {
            return null;
        }
        if (node.getLeft() != null) {
            return max(node.getLeft());
        }
        FirstNode y = node.getParent();
        while (y != null && node == y.getLeft()) {
            node = y;
            y = y.getParent();
        }

        return y;
    }

    /**
     * 查找一个节点的后继节点
     * @param node
     * @return
     */
    public static FirstNode successor(FirstNode node) {
        if (node == null) {
            return null;
        }
        if (node.getRight() != null) {
            return min(node.getRight());
        }
        FirstNode y = node.getParent();
        //保证这个父节点是由右子节点找到的
        while (y != null && node == y.getRight()) {
            node = y;
            y = y.getParent();
        }

        return y;
    }

    /**
     * 返回最小节点
     * @return
     */
    public static FirstNode min(FirstNode fnode) {
        FirstNode node = fnode;
        FirstNode min = fnode;
        while (node != null) {
            min = node;
            node = node.getLeft();
        }
        return min;
    }

    /**
     * 需要给参数  因为一个分支也是一棵树
     * @param fnode
     * @return
     */
    public static FirstNode max(FirstNode fnode) {
        FirstNode node = fnode;
        FirstNode max = fnode;
        while (node != null) {
            max = node;
            node = node.getRight();
        }
        return max;
    }

    /**
     * 删除操作分三种情况：
     * 1 z没有子节点          只需把z删除  并把z节点的引用置为null就可以了
     * 2 z只有一个子节点  把z删除并用此子节点替代z的位置
     * 3 z有两个子节点       删除z的后继   并用后继节点的值替换 z的值
     * @param node
     */
    public static void delete(FirstNode node) {
        if (node == null) {
            return;
        }

        //z没有子节点的情况
        if (node.getRight() == null && node.getLeft() == null) {
            FirstNode parent = node.getParent();
            if (node == parent.getRight()) {
                parent.setRight(null);
            }
            if (node == parent.getLeft()) {
                parent.setLeft(null);
            }
            return;
        }

        //右节点不为空
        if (node.getRight() != null && node.getLeft() == null) {
            FirstNode parent = node.getParent();
            FirstNode right = node.getRight();
            if (node == parent.getRight()) {
                parent.setRight(right);
                right.setParent(parent);
            }
            if (node == parent.getLeft()) {
                parent.setLeft(right);
                right.setParent(parent);
            }
            return;
        }
        //左节点不为空
        if (node.getLeft() != null && node.getRight() == null) {
            FirstNode parent = node.getParent();
            FirstNode left = node.getLeft();
            if (node == parent.getRight()) {
                parent.setRight(left);
                left.setParent(parent);
            }
            if (node == parent.getLeft()) {
                parent.setLeft(left);
                left.setParent(parent);
            }
            return;
        }

        //左右节点都不为空   后继一定会在右节点分支中
        FirstNode successor = successor(node);
        delete(successor);
        node.setKey(successor.getKey());

    }

    public static void main(String[] args) {
        BisearchTree bh = new BisearchTree();
        //        int[] keys = new int[] { 15, 6, 18, 3, 17, 13, 23, 18, 20, 2, 9, 4, 33 };
        int[] keys = new int[] { 15, 6, 18, 3, 7, 2, 4, 13, 9, 17, 20 };
        for (int key : keys) {
            bh.add(key);
        }
        orderWalk(root);
        System.out.println();
        morrisOrderWalk(root);
        System.out.println();

        frontWalk(root);
        System.out.println();

        morrisProcWalk(root);
        System.out.println();

        backWalk(root);
        System.out.println();
        morrisBackWalk(root);

        //
        //        FirstNode min = min(root);
        //        System.out.println(min.getKey());
        //
        //        FirstNode max = max(root);
        //        System.out.println(max.getKey());

        //        FirstNode succ = successor(tmp);
        //        System.out.println(succ.getKey());
        //
        //        FirstNode proc = processor(tmp);
        //        System.out.println(proc.getKey());

    }

}
