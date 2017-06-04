package com.takin.emmet.algo.first;

import java.util.ArrayList;

/**
 * 最小生成树算法  时间复杂度为O(eloge) 
 * 解题思路如下：T=(V,{})，将G中的边按权值从小到大排好序，然后依次选取
 * 如果选取的边使生成树T不形成回路，则把它并入TE，如果形成回路则抛弃，直到TE中有n-1条边为止
 * 难点在于：每拿出一条边时  判断是否会形成回路
 * 
 * 总体的思路还是很清晰的
 * 
 * 
 * 
 * @since 
 * @see   
 */
public class Kruskal {

    public int[] father = null;

    /**
     * 退出条件   边的个数==节点的个数-1
     * 
     * @param edges
     * @param nodes
     * @return
     */
    public int kruskal(ArrayList<Edge> edges, ArrayList<Integer> vertix) {
        int size = edges.size();
        int sum = 0;
        father = new int[vertix.size() + 1];
        for (int i = 1; i <= vertix.size(); i++) {
            father[i] = vertix.get(i);
        }

        return sum;
    }

    public static void main(String[] args) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(new Edge(1, 2, 1));
        edges.add(new Edge(1, 3, 4));
        edges.add(new Edge(1, 6, 6));
        edges.add(new Edge(2, 4, 8));
        edges.add(new Edge(2, 5, 3));
        edges.add(new Edge(3, 6, 5));
        edges.add(new Edge(3, 5, 9));
        edges.add(new Edge(4, 5, 7));
        edges.add(new Edge(4, 6, 10));
        edges.add(new Edge(5, 6, 2));

        ArrayList<Integer> nodes = new ArrayList<Integer>();
        nodes.add(0);
        nodes.add(1);
        nodes.add(2);
        nodes.add(3);
        nodes.add(4);
        nodes.add(5);
        nodes.add(6);

        new Kruskal().kruskal(edges, nodes);
    }

    public boolean union(int a, int b) {
        int f = findParent(a);
        int t = findParent(b);

        if (f == t) {//说明是在同一个子集中
            return true;
        }
        if (f > t) {
            father[t] = f;
        } else {
            father[f] = t;
        }
        return false;
    }

    public int findParent(int t) {
        while (father[t] != t) {
            t = father[t];
        }
        return t;
    }
}

class Edge implements Comparable<Edge> {
    int start;
    int end;
    int distance;

    public Edge(int start, int end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return start + "->" + end;
    }

    @Override
    public int compareTo(Edge obj) {
        int targetDis = obj.distance;
        return distance > targetDis ? 1 : (distance == targetDis ? 0 : -1);
    }
}
