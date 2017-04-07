package com.takin.emmet.algo.first;

/**
 * 最短路径寻找算法
 * 所做的优化仅是在提取下一个最短路径时  采用堆这种数据结构
 * @since 
 * @see
 */
public class Dijkstra {

    private static int max = Integer.MAX_VALUE;
    public static char[] vertix = { 'A', 'B', 'C', 'D', 'E', 'F' };
    public static int[][] edges = { { 1, 6, 3, max, max, max }, { 6, 1, 2, 5, max, max }, { 3, 2, 1, 3, 4, max }, { max, 5, 3, 1, 2, 3 }, { max, max, 4, 2, 1, 5 }, { max, max, max, 3, 5, 1 } };

    public void dijkstra() {
        int nodes = vertix.length;
        if (edges == null || edges.length < 1) {
            return;
        }

        boolean[] s = new boolean[nodes]; //记录的是节点是否被访问过
        int[] dist = new int[nodes]; //每个节点距离
        int[] path = new int[nodes]; //记录节点的访问路径     应该记录成逐层回溯的方式

        //根据第一个节点初始化一下
        for (int i = 0; i < nodes; i++) {
            dist[i] = edges[0][i];
        }
        s[0] = true;
        path[0] = -1;
        int last = 0;
        for (int i = 1; i < nodes; i++) {
            //ut
            for (int ii : dist) {
                System.out.print(ii + " ");
            }
            System.out.println("\n----------------");
            //最外层循环对应的是每一次要查找的点

            int order = 0;
            int min = max;
            for (int d = 0; d < nodes; d++) {
                if (!s[d] && min > dist[d]) {
                    min = dist[d];
                    order = d;
                }
            }
            s[order] = true;
            path[order] = last;
            last = order;
            //更新dist中的值
            for (int j = 0; j < nodes; j++) {
                if (!s[j] && edges[order][j] < max) {
                    if (dist[j] > (edges[order][j] + dist[order])) {
                        dist[j] = edges[order][j] + dist[order];
                    }
                }
            }

        }

        for (int i : path) {
            System.out.print(i + " ");
        }

    }

    public static void main(String[] args) {
        Dijkstra dj = new Dijkstra();
        dj.dijkstra();
    }
}
