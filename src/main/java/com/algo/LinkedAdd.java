package com.algo;

import com.algo.Elem.Node;

public class LinkedAdd {
    
    public Node add(Elem e1, Elem e2) {
        Node pre = e1.getNode();
        Node qre = e2.getNode();
        Node p = pre.next;
        Node q = qre.next;
        Node result = p;
        while (p != null && q != null) {
            if (p.exp < q.exp) {
                pre = p;
                p = p.next;
            } else if (p.exp > q.exp) {
                Node temp = q.next;
                pre.next = q;
                q.next = p;
                q = temp;
            } else {
                p.coef = p.coef + q.coef;
                if (p.coef == 0) {
                    pre.next = p.next;
                    p = pre.next;
                } else {
                    pre = p;
                    p = p.next;
                }
                qre.next = q.next;
                q = qre.next;
            }
        }
        if (q != null) {
            pre.next = q;
        }
        return result;
    }

    public static void main(String[] args) {
        Elem node1 = new Elem();
        node1.insert(7, 0);
        node1.insert(12, 3);
        node1.insert(2, 8);
        node1.insert(5, 12);

        Elem node2 = new Elem();
        node2.insert(4, 1);
        node2.insert(6, 3);
        node2.insert(2, 8);
        node2.insert(5, 20);
        node2.insert(7, 28);
        LinkedAdd l = new LinkedAdd();
        Node node = l.add(node1, node2);
        while (node != null) {
            System.out.println("coef:" + node.coef + "      exp:" + node.exp);
            node = node.next;
        }
    }
}

class Elem {
    public class Node {
        public int coef;//系数  
        public int exp;//指数  
        public Node next = null;//下一个节点  

        public Node() {
            coef = 0;
            exp = 0;
        }

        public Node(int coef, int exp) {
            this.coef = coef;
            this.exp = exp;
        }
    }

    public Node first = new Node();

    public void insert(int coef, int exp) {//添加节点  
        Node node = new Node(coef, exp);
        if (first == null) {
            first.next = node;
        } else {
            Node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
    }

    public Node getNode() {
        return first;
    }
}
